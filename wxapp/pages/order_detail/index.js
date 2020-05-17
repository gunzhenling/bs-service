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
    if ( order.picture) {
      let pic = order.picture.split("\\");
      order.pic = "http://localhost:5000/" + pic[pic.length-1].replace("bs-service/frontend/public", "");
    }
    if (order.picture_logo) {
      let picture_logo = order.picture_logo.split("\\");
      order._picture_logo = "http://localhost:5000/" + picture_logo[picture_logo.length-1].replace("bs-service/frontend/public", "");
    }
    order.specification = JSON.parse(order.specification);
    order.custom_made = JSON.parse(order.custom_made);
    order.user_address_json = JSON.parse(order.user_address_json);
    console.log(order);
    this.setData({order});
  },

})
