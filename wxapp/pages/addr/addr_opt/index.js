// pages/addr/addr_opt/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    opttile:'新增地址',
    info:"请选择省/市/区",
    //省市区
    region: ['', '', ''],
    // 服务热线的样式
    display: "fixed",
    height: "188rpx"
  },
//返回
goBack:function(e){
  wx.navigateBack();
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
    //创建节点选择器
    var query = wx.createSelectorQuery();
    query.select('.main').boundingClientRect()
    query.exec(function (res) {
      //res就是 所有标签为xxxx的元素的信息 的数组
      // console.log(res);
      //取高度
      heights = res[0].height;
      if (screenHeight - heights <= 124) {
        that.setData({
          display: '',
          height: "188rpx"
        })
      } else {
        that.setData({
          display: 'fixed',
          height: "88rpx"
        })
      }
    })
  },
  bindRegionChange: function (e) {
    console.log(e)
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value,
      info:""
    })
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