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
      order.pic = global.util.img(order.picture);
    }
    if (order.picture_logo) {
      order._picture_logo = global.util.img(order.picture_logo);
    }
    order.carrier_tracks_json = JSON.parse(order.carrier_tracks_json);
    order.specification = JSON.parse(order.specification);
    order.custom_made = JSON.parse(order.custom_made);
    order.user_address_json = JSON.parse(order.user_address_json);
    try {
      order.carrier_tracks_json.carrier_tracks.forEach((item, i) => {
        item.accept_time = global.util.formatDate(item.accept_time);
      });

    } catch (e) {
console.log(e);
    } finally {

    }
    console.log(order);
    this.setData({order});
  },

})
