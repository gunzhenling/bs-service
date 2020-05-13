// pages/orderlist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
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
  //返回
  goBack: function (e) {
    wx.navigateBack();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})