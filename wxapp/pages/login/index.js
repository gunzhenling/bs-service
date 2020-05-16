// pages/login/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: '',
    password: '',
  },
  //会员登录
  loginIn: async function(e){
    let {name, password} = this.data;
    if (!name) {
      return global.util.showToast.message('请输入昵称');
    }
    if (password.length < 6) {
      return global.util.showToast.message('请至少输入6位密码');
    }
    wx.showLoading({title:'登录中'});
    let res = await global.http.post('/api/bs/user/login', {name, password});
    if (res.user_id) {
      let user = Object.assign( {name, password}, res);
      global.http.setUser(user);
      wx.setStorageSync('user', user);
      global.util.showToast.message('登录成功').then(e => wx.reLaunch({url:"/pages/index/index"}));
    } else {
      global.util.showToast.message(res.message);
    }
  },

  change: function (e) {
    console.log(e);
    let {key} = e.currentTarget.dataset;
    console.log({[`${key}`]: e.detail.value});
    this.setData({[`${key}`]: e.detail.value});
  },
  //跳转到注册页面
  gotoregister:function(e){
      wx.redirectTo({
        url: '../register/index',
      })
  },
  //返回
  goBack:function(e){
    wx.navigateBack();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({showBack:  getCurrentPages().length != 1})
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
