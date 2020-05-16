// pages/orderlist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    order: wx.getStorageSync("order_detail")
  },
  onLoad: async function (options) {
    this.setData({
      order: {...wx.getStorageSync("order_detail"),...wx.getStorageSync("gift")}
    });
    setTimeout(() => {
      console.log(this.data.order);
    }, 200)
  },

})
