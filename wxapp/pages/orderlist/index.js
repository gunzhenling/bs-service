// pages/orderlist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      offset: 0,
      limit: 10,
      hasMore: true,
    //页面切换
    currentIndex1: true,//待付款
    currentIndex2: false,//待发货
    currentIndex3: false,//待收货
    currentIndex4: false,//已完成
    loadNum: 2,//待付款个数
    inNum: 3,//待发货个数
    outNum: 3,//待收货个数
    finishNum: 3,//已完成个数
    //checkbox是否显示
    isCheckbox: false,
    //是否全选
    select_all: false,
    //待付款数据
    loadList: [
      { id: 1, num: "D19060122", fengmian: '../../images/lipins/lipin1.jpg', time: "2019-03-02" },
      { id: 2, num: "D19060123", fengmian: '../../images/lipins/lipin2.jpg', time: "2019-03-02" }
    ],
    //待发货数据
    inList: [
      { id: 1, num: "A19060122", fengmian: '../../images/lipins/lipin2.jpg', time: "2019-03-02" },
      { id: 2, num: "A19060123", fengmian: '../../images/lipins/lipin3.jpg', time: "2019-03-02" },
      { id: 3, num: "A19060124", fengmian: '../../images/lipins/lipin4.jpg', time: "2019-03-02" },
      { id: 4, num: "A19060124", fengmian: '../../images/lipins/lipin1.jpg', time: "2019-03-02" },
    ],
    //待收货数据
    outList: [
      { id: 1, num: "A19060125", fengmian: '../../images/lipins/lipin3.jpg', time: "2019-03-02" },
      { id: 2, num: "A19060126", fengmian: '../../images/lipins/lipin2.jpg', time: "2019-03-02" },
      { id: 3, num: "A19060127", fengmian: '../../images/lipins/lipin4.jpg', time: "2019-03-02" },
      { id: 4, num: "A19060127", fengmian: '../../images/lipins/lipin1.jpg', time: "2019-03-02" },
    ],
    //已完成数据
    finishList: [
      { id: 1, num: "A19060128", fengmian: '../../images/lipins/lipin2.jpg', time: "2019-03-02" },
      { id: 2, num: "A19060129", fengmian: '../../images/lipins/lipin3.jpg', time: "2019-03-02" },
      { id: 3, num: "A19060120", fengmian: '../../images/lipins/lipin1.jpg', time: "2019-03-02" }
    ],
    //在仓的导航栏样式
    kong2: false
  },

  //待付款
  currentIndex1: function (e) {
    // this.onShow()
    this.setData({
      kong2: false,
      currentIndex1: true,
      currentIndex2: false,
      currentIndex3: false,
      currentIndex4: false
    })
  },
  //待发货
  currentIndex2: function (e) {
    // this.onShow(),
    this.setData({
      kong2: true,
      currentIndex1: false,
      currentIndex2: true,
      currentIndex3: false,
      currentIndex4: false
    })
  },
  //待收货
  currentIndex3: function (e) {
    // this.onShow()
    this.setData({
      kong2: false,
      currentIndex1: false,
      currentIndex2: false,
      currentIndex3: true,
      currentIndex4: false
    })
  },
  //已完成
  currentIndex4: function (e) {
    // this.onShow()
    this.setData({
      kong2: false,
      currentIndex1: false,
      currentIndex2: false,
      currentIndex3: false,
      currentIndex4: true
    })
  },
  onLoad: async function (options) {
    this.getData();
  },

  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/order/get/list', {offset,limit});
    let loadList = res.data;
    loadList.forEach((e, i) => {
      e._order_no = e.order_no.substr(22,10);
    });

    if (offset != 0) {
      loadList = this.data.loadList.concat(loadList);
    }
    this.setData({hasMore: loadList.length < res.total, loadList});
    wx.hideLoading();
  },
  pay: async function (e) {
    let item = e.currentTarget.dataset.item;
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/order/pay`, {order_no: item.order_no, pay_channel: "YuE"});
    if (!res.code) {
      await global.util.showToast.message('支付成功');
      this.setData({offset: 0});
      this.getData();
    } else {
      global.util.showToast.hide();
      global.util.showToast.message(res.message);
    }
  },

})
