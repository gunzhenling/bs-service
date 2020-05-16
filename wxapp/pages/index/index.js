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
    bargainList: [ ],
    topicList: [ ]

  },
  onLoad: async function () {
    let user = wx.getStorageSync('user');
    if (!user) {
      wx.reLaunch({url: "/pages/login/index"});
      return ;
    }
    this.getTops();
    this.getData();
  },
  onReachBottom: async function () {
    let {limit,hasMore,offset} = this.data;
    if (!hasMore) return ;
    this.setData({offset: offset+limit});
    this.getData();
  },
  getTops: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/gift/get/list', {offset:0,limit:5});
    let topicList = res.data;
    topicList.forEach((item, i) => {
      let pic = item.picture.split("\\");
      item.pic = "http://localhost:5000/" + pic[pic.length-1].replace("bs-service/frontend/public", "");
    });
    this.setData({hasMore: topicList.length < res.total, topicList});
    wx.hideLoading();
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/gift/get/list', {offset,limit});
    let bargainList = res.data;
    bargainList.forEach((item, i) => {
      let pic = item.picture.split("\\");
      item.pic = "http://localhost:5000/" + pic[pic.length-1].replace("bs-service/frontend/public", "");
    });

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
  },
  goDetailDZ: function (e) {
    wx.setStorageSync('gift', e.currentTarget.dataset.item);
    wx.navigateTo({
      url: './goodsDetail/goodsDetail?dz=1'
    })
  }
})
