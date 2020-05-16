// pages/cart/cart.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isBlank: true,
    goodsList: [],
    guessList: [
      {
        id: 1,
        img: "../../images/lipins/lipin1.jpg",
        title: "手机LED补光灯 手机拍照补光灯、闪光灯 自拍神器 10元以下小礼品",
        price: "3999",
        sold: "1258"
      },
      {
        id: 2,
        img: "../../images/lipins/lipin2.jpg",
        title: "VR眼镜手机科幻游戏  虚拟现实眼镜 多功能3D家庭影院",
        price: "3999",
        sold: "1258"
      },
      {
        id: 3,
        img: "../../images/lipins/lipin3.jpg",
        title: "吃鸡耳机头戴式 电脑电竞游戏绝地求生带麦",
        price: "3999",
        sold: "1258"
      },
      {
        id: 4,
        img: "../../images/lipins/lipin4.jpg",
        title: "复古迷你掌上游戏机3.0英寸大屏 内置168款游戏 怀旧掌机",
        price: "3999",
        sold: "1258"
      }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad:async function (options) {
    this.getData();
  },
  getData: async function () {
    global.util.showToast.loading();
    let res = await global.http.get(`/api/bs/order/get/shops`);
    let list = res.data;
    list.forEach((item, i) => {
      item.specification = JSON.parse(item.specification);
      item.custom_made = JSON.parse(item.custom_made);
    });
    this.setData({goodsList: list});
    global.util.showToast.hide();
  },
  delGoods: async function() {
    wx.showModal({
      title: '',
      content: '确定要删除改商品？',
      success: async function(res) {
        if (res.confirm) {
          let res = await global.http.get(`/api/bs/order/get/shops`);

        }
      }
    })
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
