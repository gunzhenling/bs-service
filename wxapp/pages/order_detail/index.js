// pages/orderlist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    order: ''
  },
  onLoad: async function (options) {
    let order = wx.getStorageSync("order");
    console.log(order);
    this.setData({order});
  },

})
