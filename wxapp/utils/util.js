import config from "./config.js";

const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

export const formatDate = (time, format = "YYYY-MM-DD hh:mm:ss") => {
  if (time == '' || time == null || time == undefined)
    return ''
  time = new Date(time)
  const datetime = new Date()
  datetime.setTime(time)
  const year = datetime.getFullYear()
  const month = datetime.getMonth() + 1 < 10
    ? '0' + (
    datetime.getMonth() + 1)
    : datetime.getMonth() + 1
  const date = datetime.getDate() < 10
    ? '0' + datetime.getDate()
    : datetime.getDate()
  const hour = datetime.getHours() < 10
    ? '0' + datetime.getHours()
    : datetime.getHours()
  const minute = datetime.getMinutes() < 10
    ? '0' + datetime.getMinutes()
    : datetime.getMinutes()
  const second = datetime.getSeconds() < 10
    ? '0' + datetime.getSeconds()
    : datetime.getSeconds()
  const timeArr = [
    year,
    month,
    date,
    hour,
    minute,
    second
  ]
  let _mapper = {};
  [
    'YYYY',
    'MM',
    'DD',
    'hh',
    'mm',
    'ss'
  ].forEach((_symbol, i) => (_mapper[_symbol] = timeArr[i]))
  if (format) {
    const matchArr = format.match(/\w+/g)
    matchArr.forEach(match => {
      format = format.replace(match, _mapper[match])
    })
    return format
  } else {
    return (year + '月' + month + '月' + date + '日 ' + ' ' + hour + ':' + minute)
  }
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

const img = (img) => {
  return img ? `${config.ip}:9998${img.replace("/frontend/public/images", "").replace("/frontend/images", "")}` : '';
}

module.exports = {
  formatTime,showToast, img,formatDate
}
