// pages/dingzhilist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    offset: 0,
    limit: 10,
    hasMore: true,
    dingzhilist: [
      { id: 1, name: 'VR设备定制', price: '3000.00', fengmian: '../../images/tuijians/tuijian1.png' },
      { id: 2, name: '耳机定制粉色', price: '80.00', fengmian: '../../images/tuijians/tuijian2.png' },
      { id: 3, name: '玫瑰礼盒定制', price: '23.00', fengmian: '../../images/tuijians/tuijian3.png' }
    ]
  },
  //返回
  goBack(){
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
    let res = await global.http.get('/api/bs/gift/get/list', {made_type:1,offset,limit});
    let dingzhilist = res.data;
    dingzhilist.forEach((item, i) => {
      item.pic = global.util.img(item.picture);
    });

    if (offset != 0) {
      dingzhilist = this.data.dingzhilist.concat(dingzhilist);
    }
    this.setData({hasMore: dingzhilist.length < res.total, dingzhilist});
    wx.hideLoading();
  },
  goDetailDZ: function (e) {
    wx.setStorageSync('gift', e.currentTarget.dataset.item);
    wx.navigateTo({
      url: '/pages/index/goodsDetail/goodsDetail?dz=1'
    })
  },
})
