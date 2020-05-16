// pages/addr/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addrList:[
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
    wx.setStorageSync("address", e.currentTarget.dataset.item || '');
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
  setdefault:async function(e){
    let item = e.currentTarget.dataset.item;
    if (item.is_default) {
      return ;
    }
    item.is_default = 1;
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/address/update`, item);
    // NOTE: 更新
    res = await global.http.get('/api/bs/address/get/list');
    global.util.showToast.hide();
    res.sort((e1,e2) => e2.is_default-e1.is_default)
    this.setData({addrList: res});
  },
  // optdel
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: async function (options) {
    this.setData({select: options.select})
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

  onShow: async function () {
    global.util.showToast.loading();
    let res = await global.http.get('/api/bs/address/get/list');
    global.util.showToast.hide();
    res.sort((e1,e2) => e2.is_default-e1.is_default)
    this.setData({addrList: res});
  },
  clickAddr (e) {
    if (this.data.select) {
      wx.setStorageSync("s_address",  e.currentTarget.dataset.item )
      wx.navigateBack();
    }
  }
})
