// pages/editpwd/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    old_password: "",
    password: "",
    confirm_password: "",
  },
  //返回
  goBack:function(e){
    wx.navigateBack();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  //会员登录
  confirm: async function(e){
    let {old_password, password, confirm_password,} = this.data;
    if (password.length < 6) {
      return global.util.showToast.message('请至少输入6位新密码');
    }
    if (password!==confirm_password) {
      return global.util.showToast.message('两次密码不一致');
    }
    wx.showLoading({title:'加载中'});
    let res = await global.http.post('/api/bs/user/login', {name: wx.getStorageSync('user').name, old_password, password, confirm_password,});
    if (!res.code) {
      global.util.showToast.message('修改密码成功').then(e => wx.navigateBack());
    } else {
      global.util.showToast.message(res.message);
    }
  },

  change: function (e) {
    let {key} = e.currentTarget.dataset;
    this.setData({[`${key}`]: e.detail.value});
  },

})
