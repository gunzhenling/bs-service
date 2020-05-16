//index.js

Page({
  data: {
    offset: 0,
    limit: 10,
    hasMore: true,
    slides: [
      "../../images/slides/banner1.jpg",
      "../../images/slides/banner2.jpg",
      "../../images/slides/banner3.jpg"
    ],
    bargainList: [
    ],
    topicList: [
      {
        img: "../../images/tuijians/tuijian1.png",
        title: "加湿净化小礼品市集",
        subTitle: "舒适健康的办公环境是非常重要的，尤其在多人的办公室里面...",
        price: "91"
      },
      {
        img: "../../images/tuijians/tuijian2.png",
        title: "厨房创意实用帮手小礼品",
        subTitle: "厨房在一套房子里面的功能，不用说，就是料理家人...",
        price: "23"
      },
      {
        img: "../../images/tuijians/tuijian3.png",
        title: "提升家里蹲幸福感的好玩礼品",
        subTitle: "小集今天来为各位小伙伴们推荐提升家...",
        price: "129"
      }
    ]

  },
  onLoad: async function () {
    let user = wx.getStorageSync('user');
    if (!user) {
      wx.reLaunch({url: "/pages/login/index"});
      return ;
    }
    this.getData();
  },
  onReachBottom: async function () {
    let {limit,hasMore,offset} = this.data;
    if (!hasMore) return ;
    this.setData({offset: offset+limit});
    this.getData();
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/gift/get/list', {offset,limit});
    let bargainList = res.data;
    if (offset != 0) {
      bargainList = this.data.bargainList.concat(bargainList);
    }
    this.setData({hasMore: bargainList.length < res.total, bargainList});
    wx.hideLoading();
  },
  //跳转定制
  goDingZhiList:function(e){
    wx.navigateTo({
      url: '../../pages/dingzhilist/index',
    })
  },
  //跳转公告
  goNoticeList:function(){
    wx.navigateTo({
      url: '../noticelist/index',
    })
  },
  //跳转操作指南
  goHelpList:function(){
    wx.navigateTo({
      url: '../helplist/index',
    })
  },
  goDetail: function (e) {
    wx.setStorageSync('gift', e.currentTarget.dataset.item);
    wx.navigateTo({
      url: './goodsDetail/goodsDetail'
    })
  }
})
