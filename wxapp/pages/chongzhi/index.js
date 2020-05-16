// pages/chongzhi/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    money: "",
  },
  //确认充值
  submit:async function(e){
    let {money} = this.data;
    money = Number(money);
    if (!money) {
      global.util.showToast.message("请输入有效的充值金额");
      return ;
    }
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/user/recharge/available`, {money});
    if (!res.code) {
      await global.util.showToast.message('充值成功');
      wx.navigateBack();
    } else {
      global.util.showToast.hide();
      global.util.showToast.message(res.message);
    }
  },
  //返回
  goBack:function(e){
    wx.navigateBack();
  },
  change (e) {
    this.setData({money: +e.detail.value});
  }

})
