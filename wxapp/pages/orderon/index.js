// pages/helplist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: '',
    gift: '',
  },
  onLoad() {
    this.setData({gift: wx.getStorageSync('gift')});
  },
  onShow: async function () {
    let address =  wx.getStorageSync('s_address');
    if (!address) {
      global.util.showToast.loading();
      let res = await global.http.get('/api/bs/address/get/list');
      global.util.showToast.hide();
      address = res[0];
    }
    this.setData({address});
  },
  goBack: function (e) {
    wx.navigateBack();
  },
  goAddr () {
    wx.navigateTo({url: "/pages/addr/index?select=1"})
  },
  orderon: async function () {
    let {gift,address} = this.data;
    if (!address || !address.user_address_id) return global.util.showToast.message("请选择收货地址");
    if (gift.custom_made && gift.custom_made.made_type == 1) {
      if (!gift.picture_logo) return global.util.showToast.message("请先上传定制logo图片");
    }
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/order/commit`, {
      gift_code: gift.gift_code,
      gift_amount: gift.gift_amount || gift.buy_num,
      sell_income: gift.sell_income,
      buyer_pay_amount: gift.buyer_pay_amount,
      picture: gift.picture,
      specification: gift.specification,
      custom_made: gift.custom_made,
      picture_logo: gift.picture_logo,
      user_address_id: address.user_address_id,
    });
    if (!res.code) {
      let order = res;
      await global.util.showToast.message('下单成功');
      global.util.showToast.loading();
      // NOTE: 删除购物车
      if (gift.id && gift.expiration_time) {
        let c = await global.http.post(`/api/bs/order/update/shops/${gift.id}/0`);
        console.log(c);
      }
      res = await global.http.post(`/api/bs/order/pay`, {order_no: order.order_no, pay_channel: "YuE"}, {no_toast: 1});
      if (!res.code) {
        await global.util.showToast.message('支付成功');
        wx.redirectTo({url: "/pages/orderlist/index?current=2"})
      } else {
        global.util.showToast.hide();
        await global.util.showToast.message(res.message);
        wx.redirectTo({url: "/pages/orderlist/index?current=1"})
      }
    } else {
      global.util.showToast.hide();
      global.util.showToast.message(res.message);
    }
  },
  uploadFile: async function () {
    var that = this;

    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      success: function(res) {
        wx.showLoading({ title: '上传中...', mask: true })
        wx.uploadFile({
          url: `${global.host}/api/bs/file/upload/image`,
          filePath: res.tempFilePaths[0],
          name: 'picture_file',
          fileType: 'image',
          success(res) {
            res = JSON.parse(res.data);
            if (res.code) {
              global.util.showToast.message(res.message)
            } else {
              global.util.showToast.hide();
              that.data.gift.picture_logo = res.result;
              that.data.gift._picture_logo = global.util.img(res.result);
              that.setData({gift: {...that.data.gift}});
            }
          }
        })
      },
    })
  }
})
