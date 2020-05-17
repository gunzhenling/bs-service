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
  onReachBottom: async function () {
    let {limit,hasMore,offset} = this.data;
    if (!hasMore) return ;
    this.setData({offset: offset+limit});
    this.getData();
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/user/giftLike/getList', {offset,limit});
    let guessList = res.data;
    guessList.forEach((item, i) => {
      item.img = global.util.img(item.picture);
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
