// pages/cart/cart.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isBlank: true,
    goodsList: [],
    guessList: [
    ]
  },

  onShow:async function (options) {
    this.getData();
  },
  getData: async function () {
    global.util.showToast.loading();
    let res = await global.http.get(`/api/bs/order/get/shops`);
    let list = res.data;
    list.forEach((item, i) => {
      item.specification = JSON.parse(item.specification);
      item.custom_made = JSON.parse(item.custom_made);
      item.pic = global.util.img(item.picture);
    });
    console.log(list);
    if (list.length == 0) {
      this.getGuessList();
    }
    this.setData({goodsList: list});
    global.util.showToast.hide();
  },
  delGoods: async function(e) {
    let item = e.currentTarget.dataset.item;
    wx.showModal({
      title: '',
      content: '确定要删除该商品？',
      success: async res => {
        if (res.confirm) {
          global.util.showToast.loading();
          let res = await global.http.post(`/api/bs/order/update/shops/${item.id}/0`);
          global.util.showToast.hide();
          if (res.code) {
            global.util.showToast.message(res.message);
          } else {
            global.util.showToast.message("删除成功");
            this.getData();
          }
        }
      }
    })
  },
  c_buy_num: async function (e) {
    let {item, num} = e.currentTarget.dataset;
    let amount = Number(item.gift_amount) + Number(num);
    if (amount == 0) {
      this.delGoods(e);
      return ;
    }
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/order/update/shops/${item.id}/${amount}`, {gift_amount: amount});
    global.util.showToast.hide();
    this.getData();
  },
  orderon: async function(e) {
    let item = e.currentTarget.dataset.item;
    // item.custom_made = JSON.parse(item.custom_made);
    // item.specification = JSON.parse(item.specification);
    item.pic = global.util.img(item.picture);
    wx.setStorageSync("gift", item);
    wx.navigateTo({url: "/pages/orderon/index"})
  },
  getGuessList: async function () {
    let offset = Math.floor(Math.random()*20);
    wx.showLoading();
    let res = await global.http.get('/api/bs/gift/get/list', {made_type:0,offset,limit:4});
    let guessList = res.data;
    guessList.forEach((item, i) => {
      item.pic = global.util.img(item.picture);
    });
    this.setData({guessList});
    wx.hideLoading();
  },
  goDetail: function (e) {
    wx.setStorageSync('gift', e.currentTarget.dataset.item);
    wx.navigateTo({
      url: '/pages/index/goodsDetail/goodsDetail'
    })
  },
})
