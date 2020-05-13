// pages/helplist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    storageIntroduce: true,
    howStorage: true,
    howRetrieved: true,
    feeScale: true,
    insurance: true,
    storageLimit: true,
    protocol: true,
    display: 'fixed'
  },
  //返回
  goBack() {
    wx.navigateBack();
  },
  //礼品定制介绍
  storageIntroduce() {
    this.onLoad();
    var storageIntroduces = !this.data.storageIntroduce
    this.setData({ storageIntroduce: storageIntroduces })
  },
  //如何让存放物品
  howStorage() {
    this.onLoad();
    var howStorages = !this.data.howStorage
    this.setData({ howStorage: howStorages })
  },
  //如何取回物品
  howRetrieved() {
    this.onLoad();
    var howRetrieveds = !this.data.howRetrieved
    this.setData({ howRetrieved: howRetrieveds })
  },
  //收费标准
  feeScale() {
    this.onLoad();
    var feeScales = !this.data.feeScale
    this.setData({ feeScale: feeScales })
  },
  //安全保障
  insurance() {
    this.onLoad();
    var insurances = !this.data.insurance
    this.setData({ insurance: insurances })
  },
  //存放品限制
  storageLimit() {
    this.onLoad();
    var storageLimits = !this.data.storageLimit
    this.setData({ storageLimit: storageLimits })
  },
  //用户服务协议
  protocol() {
    this.onLoad();
    var protocols = !this.data.protocol
    this.setData({ protocol: protocols })
  },
  onHide() {
    this.setData({
      storageIntroduce: true,
      howStorage: true,
      howRetrieved: true,
      feeScale: true,
      insurance: true,
      storageLimit: true,
      protocol: true,
      display: 'fixed'
    })
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