// pages/jiaoyilist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bank_user: '',
    currentIndex1: true,
    currentIndex2: false,
    recharge: [{
        id: 1,
        money: "1000.00",
        time: "2019-03-26"
      },
    ],
    consume: [{
        id: 4,
        money: "1000.00",
        index: "A20192438",
        time: "2019-03-26"
      }
    ],
    height: ''
  },
  //返回
  goBack: function(e) {
    wx.navigateBack();
  },
  //点击充值跳转充值页面
  gotochongzhi() {
    wx.navigateTo({
      url: '../chongzhi/index',
    })
  },
  currentIndex1: function(e) {
    this.setData({
      currentIndex1: true,
      currentIndex2: false,
    })
  },
  currentIndex2: function(e) {
    this.setData({
      currentIndex1: false,
      currentIndex2: true,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // /bs/user/get/available/details
    var that = this;
    var screenHeight, heights
    wx.getSystemInfo({
      success: function(res) {
        screenHeight = res.screenHeight
      }
    });
    // heights = screenHeight-382
    // this.setData({
    //   height:heights
    // })
    // /bs/user/get/available/details
  },
  onShow () {
    this.getData();
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/user/get/available/details', {offset,limit});
    this.setData({
      bank_user: res.bank_user_dto,
      consume: res.bank_user_available_history_dtos.filter(e => e.is_reduce != 0),
      recharge: res.bank_user_available_history_dtos.filter(e => e.is_reduce == 0)
    });
    console.log(this.data.consume);
    console.log(this.data.recharge);
    console.log(res);
    // let loadList = res.data;
    // loadList.forEach((e, i) => {
    //   e._order_no = e.order_no.substr(22,10);
    // });
    //
    // if (offset != 0) {
    //   loadList = this.data.loadList.concat(loadList);
    // }
    // this.setData({hasMore: loadList.length < res.total, loadList});
    wx.hideLoading();
  },
})
