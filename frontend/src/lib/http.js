import { reLogin,loading, toast } from "./utils";
import {message} from 'ant-design-vue';

let vue = "";

const getResponseHeaders = (res, key) => {
  let value = ""
  try {
    value = res.headers.map[key][0]
  } catch (e) {

  } finally {

  }
  return value;
}

let loadingCount = 0
const openLoading = () => {
  if (!loading.opening()) {
    loadingCount = 0;
    loading.open()
  }
  loadingCount = loadingCount + 1
}
const loadComplete = () => {
  // 加延时，then之后有网络请求，先执行then再执行该函数，避免重复loading重复open闪烁
  setTimeout(() => {
    loadingCount = loadingCount - 1
    if (loadingCount === 0) {
      loading.close()
    }
  }, 0)
}

const methods = ['get', 'post', 'put', 'patch', 'delete']
var fetchRequest = {}
var user = {}
try {
  user = JSON.parse(localStorage.getItem('user') || {})
} catch (e) {} finally {}
methods.forEach(method => {
  fetchRequest[method] = (url, body, headers = {}) => {
    let options = {
      url,
      method,
      headers
    }
    if (body) {
      for (var key in body) {
        if (body[key] == undefined) delete body[key];
      }
    }
    if (method === 'get') {
      if (body && typeof body === 'object') {
        url += '?'
        for (let k in body) {
          url += (k + '=' + body[k] + '&')
        }
        url = url.substring(0, url.length - 1)
      }
      options.url = url
    } else {
      options.body = JSON.stringify(body)
    }

    if (typeof options.headers !== 'object') {
      options.headers = {}
    }
    if (user && (user.access_token)) {
      options.headers['admin_token'] = `${user.access_token}`
    }
    options.headers.app_id = "101"
    if (headers["Content-Type"]) {
      options.headers["Content-Type"] = headers["Content-Type"]
    } else {
      options.headers["Content-Type"] = "application/json;charset=UTF-8"
    }
    for (let key in options.headers) {
      vue.http.headers.common[key] = options.headers[key] + ''
    }
    // delete options.headers
    openLoading()
    return vue.http(options).then(res => {
      loadComplete();
      let result = res.body;
      if (result.code == "8001") {
        window.location.href = "/login";
        return ;
      }
      return result;
    }, res => {
      return {code:9999, message: res.bodyText};
      // loadComplete();
      // res = res.body ? res.body : res;
      // if (res.status === 500 || res.status === 0) {
      //   // message.error(res.message || res.response);
      //   throw {code: 3333, ...res, message: res && res.message || res.response || '服务器繁忙'}
      // }
      // throw {code: 3333,...res,message: res && res.message || res.response || '服务器繁忙'}
    })
  }
})

fetchRequest.setUser = (data) => {
  if (data.access_token) {
    data.admin_token = data.access_token
  }
  user = Object.assign(user, data);
  localStorage.setItem('user', JSON.stringify(user));
}

export default (app) => {
  vue = app;
  return fetchRequest;
}
