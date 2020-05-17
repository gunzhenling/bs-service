// pages/member/member.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    user: wx.getStorageSync("user") || {}
  },
  onLoad: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/user/get/info');
    this.setData({
      user: {...res.user_resp,...res.bank_user_dto}
    })
    wx.hideLoading();
  },
  //退出登录
  loginout:function(e){
      wx.showModal({
        title:'退出登录',
        content:'您确定要安全退出吗？',
        success:function(res){
          if(res.confirm){
            wx.setStorageSync('user', '')
              wx.redirectTo({
                url: '../login/index',
              });
          }else{

          }
        }
      });
  },
  //跳转页面
  gotopage(e){
   console.log(e);
   let _url=e.currentTarget.dataset.url;
   wx.navigateTo({
     url: _url,
   })
  }
})
