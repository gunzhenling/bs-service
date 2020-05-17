// pages/shoucanglist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    offset: 0,
    limit: 10,
    hasMore: true,

    isBlank: true,
    ishavedata:true,
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
//返回
goBack:function(e){
wx.navigateBack();
},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData();
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/user/giftLike/getList', {offset,limit});
    let guessList = res.data;
    guessList.forEach((item, i) => {
      let pic = (item.picture||'').split("\\");
      item.pic = "http://localhost:5000/" + pic[pic.length-1].replace("bs-service/frontend/public", "");
    });

    if (offset != 0) {
      guessList = this.data.guessList.concat(guessList);
    }
    this.setData({hasMore: guessList.length < res.total, guessList});
    wx.hideLoading();
  },
  goDetail: function (e) {
    wx.setStorageSync('gift', e.currentTarget.dataset.item);
    wx.navigateTo({
      url: '/pages/index/goodsDetail/goodsDetail'
    })
  },
})
