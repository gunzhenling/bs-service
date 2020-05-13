// pages/dingzhilist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dingzhilist: [
      { id: 1, name: 'VR设备定制', price: '3000.00', fengmian: '../../images/tuijians/tuijian1.png' },
      { id: 2, name: '耳机定制粉色', price: '80.00', fengmian: '../../images/tuijians/tuijian2.png' },
      { id: 3, name: '玫瑰礼盒定制', price: '23.00', fengmian: '../../images/tuijians/tuijian3.png' }
    ]
  },
  //返回
  goBack(){
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