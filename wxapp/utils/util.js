const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
let loading =false;
export const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

export const showToast = {
  message: (msg, duration = 2000) =>
    new Promise(function(resolve) {
      wx.hideLoading();
      wx.showToast({title: msg, icon: "none", duration})
      setTimeout(() => {
        resolve();
      }, duration - 100); //提前关闭，防止toast消失了，还没执行end函数
    }),
  loading: (msg) => {
    loading = true;
    wx.hideLoading();
    wx.showLoading({title: msg || "加载中",icon: "loading", mask: true})
  },
  hide: (msg) => {
    loading = false;
    wx.hideLoading();
  },
  isloading: () => loading,
}

module.exports = {
  formatTime,showToast
}
