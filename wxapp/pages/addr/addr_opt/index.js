// pages/address/address_opt/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    opttile:'新增地址',
    info:"请选择省/市/区",
    //省市区
    region: ['', '', ''],
    // 服务热线的样式
    display: "fixed",
    height: "188rpx",
    name: "",
    phone: "",
    address: "",
    address_data: '',
  },
//返回
goBack:function(e){
  wx.navigateBack();
},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let address = wx.getStorageSync("address");
    if (address) {
      this.setData({
        name: address.name,
        phone: address.phone,
        address: address.address,
        region: [address.province,address.city,address.district],
        info: '',
        address_data: address,
      })
    }
    var that = this;
    var screenHeight, heights
    wx.getSystemInfo({
      success: function (res) {
        screenHeight = res.screenHeight
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
          height: "188rpx"
        })
      } else {
        that.setData({
          display: 'fixed',
          height: "88rpx"
        })
      }
    })
  },
  bindRegionChange: function (e) {
    console.log(e)
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value,
      info: ""
    })
  },
  change (e) {
    console.log(e,e.detail.value);
    let key = e.currentTarget.dataset.key;
    this.setData({[`${key}`]: e.detail.value})
  },
  save: async function () {
    let {name,phone, region, address, address_data={}} = this.data;
    console.log(name,phone, region, address, address_data);
    if (!name) return global.util.showToast.message("请填写姓名");
    if (!phone) return global.util.showToast.message("请填写联系方式");
    if (!region[0]) return global.util.showToast.message("请选择地址");
    if (!address) return global.util.showToast.message("请填写详细地址");

    global.util.showToast.loading();
    let res;
    if (address_data.user_address_id) {
       res = await global.http.post(`/api/bs/address/update` , {
        is_default: address_data.is_default, country:address_data.country,
        user_address_id: address_data.user_address_id,
        name,phone, province: region[0],city:region[1],district:region[2], address
      });
    } else {
       res = await global.http.post(`/api/bs/address/add`, {
        is_default: 0, country:"中国",
        name,phone, province: region[0],city:region[1],district:region[2], address
      });
    }
    global.util.showToast.hide();
    if (!res.code) {
      global.util.showToast.message('保存成功').then(e => wx.navigateBack());
    } else {
      global.util.showToast.message(res.message);
    }
  }
})
