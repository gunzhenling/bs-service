import {
  showToast
} from './util';
import config from "./config.js";

const apiHelper = function(url) {
  if (url && url.indexOf('http') === 0) {
    return url;
  } else {
    return config.host + url;
  }
};

let user = wx.getStorageSync("user") || {};

let defaultOpts = {
  header: {
    'Content-Type': 'application/json; charset=utf-8',
  }
};

const setDefault = function(options = {}) {
  defaultOpts = Object.assign(defaultOpts, options);
};

const setUser = function(e) {
  user = e;
}

const requestPromise = (url, method, data, options) => {
  data.user_id = user.user_id;
  return new Promise(function(resolve, reject) {
    wx.request(
      Object.assign({
          url: apiHelper(url),
          method,
          data,
          success: function(res) {
            console.log(res)
            let statusCode = res.statusCode;
            // if (statusCode === 401) {
            //   wx.removeStorageSync("user");
            //   isLogin().then(res => {
            //     resolve(requestPromise(url, method, data, options));
            //   });
            // }
            let response = res.data;
            if (statusCode == 200) {
              if (!response.code || response.code == 3005) {
                if (response.code == 3005) {
                  resolve({
                    register_code: res.header.register_code
                  });
                  return;
                }
                resolve(response.result !== undefined ? response.result : response);
              } else {
                showToast.message(response.message);
                reject(response.result !== undefined ? response.result : response);
              }
            }
          },
          fail: function(e) {
            // #ifdef MP-ALIPAY
            // let statusCode = e.statusCode;
            // if (statusCode === 401) {
            //   wx.removeStorageSync("user");
            //   console.log('denglu')
            //   isLogin().then(res => {
            //     resolve(requestPromise(url, method, data, options));
            //   });
            //   return
            // }
            // #endif

            const errMsg = '服务器繁忙, 请稍后再试';
            if (
              options.error &&
              typeof options.error === 'function'
            ) {
              showToast.message(errMsg);
              return options.error(new Error(errMsg), reject);
            } else {
              showToast.message(errMsg);
              reject(e)
            }
          }
        },
        options
      )
    );
  });
};

function requestMethod(method = 'GET') {
  return function(url, data = {}, header = {}) {
    let options = Object.assign({}, defaultOpts);
    options.header = Object.assign(options.header, header);
    return requestPromise(
      url,
      method,
      data,
      options
    );
  };
}

module.exports = {
  get: requestMethod('GET'),
  post: requestMethod('POST'),
  put: requestMethod('PUT'),
  delete: requestMethod('DELETE'),
  setDefault,
  setUser,
};
