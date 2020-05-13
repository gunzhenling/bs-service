// pages/noticelist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    info: [
      {
        id: 0, name: "服务器升级公告", content: "亲爱的用户：因数据中心割接，从即日起到 1 月 9 日中午 12 点，我们将对服务器做一些优化和升级，本次服务器升级涉及范围广，因此会对功能有较大影响，在此期间我们将暂停部分功能，在此期间，您仍可使用搜索、浏览等功能。我们有完善的数据安全措施，您的图片等数据不会受到任何影响，请放心。抱歉给您带来不便。", time: "2019-01-03 15:00:34", notice: true,
      },
      {
        id: 1, name: "服务器升级公告", content: "亲爱的用户：因数据中心割接，从即日起到 1 月 9 日中午 12 点，我们将对服务器做一些优化和升级，本次服务器升级涉及范围广，因此会对功能有较大影响，在此期间我们将暂停部分功能，在此期间，您仍可使用搜索、浏览等功能。我们有完善的数据安全措施，您的图片等数据不会受到任何影响，请放心。抱歉给您带来不便。", time: "2019-01-03 15:00:34", notice: true,
      },
    ],
    display: "fixed"
  },
  //服务器升级消息
  notice(e) {
    this.onLoad();
    var id = e.currentTarget.id
    var notices = !this.data.info[id].notice
    var notice = "info[" + id + "].notice"
    this.setData({
      [notice]: notices
    })
  },
  //返回
  goBack(){
    wx.navigateBack();
  },
  onHide() {
    var infos = this.data.info
    for (i in infos) {
      infos[i].notice = true
    }
    this.setData({
      info: infos
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