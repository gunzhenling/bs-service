// pages/jiaoyilist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentIndex1: true,
    currentIndex2: false,
    recharge:[
      { id: 1, money: "1000.00", time: "2019-03-26" },
      { id: 2, money: "1000.00", time: "2019-03-26" },
      { id: 3, money: "1000.00", time: "2019-03-26" },
      { id: 4, money: "1000.00", time: "2019-03-26" },
    ],
    consume:[
      { id: 1, money: "1000.00", index: "A20192438", time: "2019-03-26" },
      { id: 2, money: "1000.00", index: "A20192438", time: "2019-03-26" },
      { id: 3, money: "1000.00", index: "A20192438", time: "2019-03-26" },
      { id: 4, money: "1000.00", index: "A20192438", time: "2019-03-26" }
    ],
    height:''
  },
  //返回
  goBack:function(e){
    wx.navigateBack();
  },
//点击充值跳转充值页面
gotochongzhi(){
  wx.navigateTo({
    url: '../chongzhi/index',
  })
},
currentIndex1: function (e) {
  this.setData({
    currentIndex1: true,
    currentIndex2: false,
  })
},
currentIndex2: function (e) {
  this.setData({
    currentIndex1: false,
    currentIndex2: true,
  })
},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var screenHeight, heights
    wx.getSystemInfo({
      success: function (res) {
        screenHeight = res.screenHeight
      }
    });
    // heights = screenHeight-382
    // this.setData({
    //   height:heights
    // })
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