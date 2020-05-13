// import Clipboard from 'clipboard'
// import config from "./config"

export const isMobile = location.href.indexOf("customer/mobile") !== -1

export const toast = (msg, time = 3000) => {
  let dismissTime = 300
  let node = document.createElement("div");
  node.className = "bodyToast"
  node.innerHTML = msg
  document.body.appendChild(node)
  return new Promise(resolve => {
    setTimeout(() => {
      node.setAttribute('class', "bodyToast dismiss")
      setTimeout(() => {
        document.body.removeChild(node);
        resolve();
      }, dismissTime)
    }, time - dismissTime)
  });
}

export const loading = {
  open: () => {
    let loading = document.getElementById("bodyLoading")
    if (!loading) {
      let node = document.createElement("div");
      node.id = "bodyLoading"
      node.appendChild(document.createElement("div"))
      document.body.appendChild(node)
    }
  },
  close: () => {
    let loading = document.getElementById("bodyLoading")
    if (loading) {
      document.body.removeChild(loading)
    }
  },
  opening: () => {
    return document.getElementById("bodyLoading");
  }
}

export const webEnv = () => {
  let UA = navigator.userAgent,
    isAndroid = /android|adr|linux/gi.test(UA),
    isIOS = /iphone|ipod|ipad/gi.test(UA) && !isAndroid,
    isBlackBerry = /BlackBerry/i.test(UA),
    isWindowPhone = /IEMobile/i.test(UA),
    isMobile = isAndroid || isIOS || isBlackBerry || isWindowPhone
  return {
    isAndroid: isAndroid,
    isIOS: isIOS,
    isMobile: isMobile,
    isWeixin: /MicroMessenger/gi.test(UA),
    isQQ: /QQ/gi.test(UA),
    isPC: !isMobile,
    isWeibo: /WeiBo/gi.test(UA)
  }
}

export const getValueOfKeys = (obj, keys) => {
  let value = obj;
  keys = keys.split(".");
  for (let i = 0; i < keys.length; i++) {
    if (!value) return value;
    value = value[keys[i]];
  }
  return value;
}

export const formatDate = (time, format = "YYYY-MM-DD") => {
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

export const getCountTime = (time) => {
  if (time) {
    let day = 0
    let hour = Math.floor(time / (60 * 60 * 1000))
    let minute = Math.floor(time % (60 * 60 * 1000) / (60 * 1000))
    let second = Math.floor(time % (60 * 1000) / 1000)
    if (hour > 24) {
      day = Math.floor(hour / 24)
      hour = (hour - day * 24).toFixed(0)
    }
    hour = hour < 10
      ? ("0" + hour)
      : hour
    minute = minute < 10
      ? ("0" + minute)
      : minute
    second = second < 10
      ? ("0" + second)
      : second
    return `${day && day + "天"}${hour}:${minute}:${second}`
  }
  return ''
}

export const reLogin = (path) => {
  // if (localStorage.debug) {
  //   return ;
  // }
  // path = path || location.href.split(location.host)[1]
  // sessionStorage.path = path;
  // if (webEnv().isWeibo) {
  //   window.location.href = config.authLogin.weibo;
  // } else if (webEnv().isWeixin) {
  //   window.location.href = config.authLogin.wechat;
  // } else {
  //   window.location.href = isMobile ? `/customer/mobile/login/input` : `/customer/login`;
  // }
}

export const loginSuccess = (path) => {
  if (sessionStorage.path) {
    let track = sessionStorage.path.split("#")[1];
    if (track) {
      let fromPage = track.split(">")[0];
      let toPage = track.split(">")[1];
      if (track.indexOf("导航") !== -1) {
        // add track
        _czc.push(["_trackEvent", fromPage, toPage, "未登录-登录成功", 1]);
        path = path || sessionStorage.path.split("#")[0];
        sessionStorage.path = "";
      }
    }
  }
  window.$router.replace(path || sessionStorage.path || "/customer/mobile/personal");
}

export const toError = () => {
  location.href = `/customer/${isMobile?'mobile/':''}error?path=${encodeURIComponent(location.href.split(location.host)[1])}`
}

export const copy = (id) => {
  // let clipboard = new Clipboard(id);
  // clipboard.on('success', function(e) {
  //   toast('复制到粘贴板')
  // });
  // clipboard.on('error', function(e) {
  //   toast('您的浏览器不支持直接复制, 请手动复制链接!')
  //
  // });
  // this.$router.push(`/customer/mobile/personal/order/tofriend/${param}`)
}
// NOTE: "10": "用户取消订单", "11": "待付款", "20": "支付超时关闭", "21": "待成团", "22": "送好友，待填地址", "25": "待发货",
// "26": "已发货", "27": "已收货", "28": "订单结算，完成", "30": "用户退款关闭", "31": "21/22-退款中", "32": "25-退款中",
// "33": "同意退款退货（无用）", "34": "申请退货退款", "35": "同意退款退货（无用）", "36": "申请无货退款", "40": "用户退货关闭", "42": "冻结中", "50": "系统退款关闭",
export const orderStatus = {
  cancelled: "10",// 取消订单
  pending: "11",
  payCancelled: "20",// 支付超时关闭
  waitGroup: "21",
  waitToFriend: "22",
  processing: "25",
  delivering: "26",
  completed: "27",
  finish: "28",
  refunded: "30",
  refundingSys: "31",
  refunding: "32",
  applyReturning: "34",
  returned: "40",
  returning: "35",
  onhold: "42",
  closed: "50",// 系统退款关闭
  refundedF: "60",// 商家无货退款
}

export default {
  copy,
  reLogin,
  loginSuccess,
  toError,
  formatDate,
  getCountTime,
  webEnv,
  isMobile,
  toast,
  loading,
  orderStatus,
}
