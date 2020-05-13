// pages/addr/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addrList:[
      {id:1,tel:13636363636,name:"小猪佩奇",addr:"浙大网新智慧立方D幢202室",index:1},
      { id: 2, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 2,checked:true },
      { id: 3, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 4, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 5, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 6, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 7, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 8, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 9, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 },
      { id: 10, tel: 13636363636, name: "小猪佩奇", addr: "浙大网新智慧立方D幢202室", index: 1 }
    ],
    // 服务热线的样式
    display:"fixed",
    height:"188rpx"
  },
//返回
  goBack:function(e){
      wx.navigateBack();
  },
  //新增 或 修改
  gotoopt:function(e){
    console.log(e);
    wx.navigateTo({
      url: '../addr/addr_opt/index',
    })
  },
  //删除
  optdel:function(e){
      wx.showModal({
        title:'删除',
        content:'您确定要删除吗?',
        success:function(res){
          if(res.confirm){
              wx.showToast({title:'删除成功',icon:'success',duration:2000});
          }else{

          }
        }
      });
  },
  //设为默认值
  setdefault:function(e){
    wx.showToast({title:'设置成功',icon:'success',duration:2000});
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    var screenHeight, heights
    wx.getSystemInfo({
      success: function (res) {
        screenHeight = res.screenHeight
        // console.log(res.screenHeight)
      }
    });
    //创建节点选择器
    var query = wx.createSelectorQuery();
    query.select('.main').boundingClientRect()
    query.exec(function (res) {
      //res就是 所有标签为xxxx的元素的信息 的数组
      // console.log(res);
      //取高度
      heights = res[0].height;
      if (screenHeight - heights <= 124) {
        that.setData({
          display: '',
          height:"188rpx"
        })
      } else {
        that.setData({
          display: 'fixed',
          height: "88rpx"
        })
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