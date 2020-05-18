// pages/orderlist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      offset: 0,
      limit: 10,
      hasMore: true,
      current: 1,
    //页面切换
    currentIndex1: true,//待付款
    currentIndex2: false,//待发货
    currentIndex3: false,//待收货
    currentIndex4: false,//已完成
    loadNum: 2,//待付款个数
    inNum: 3,//待发货个数
    outNum: 3,//待收货个数
    finishNum: 3,//已完成个数
    //checkbox是否显示
    isCheckbox: false,
    //是否全选
    select_all: false,
    //待付款数据
    loadList: [
      // { id: 1, num: "D19060122", fengmian: '../../images/lipins/lipin1.jpg', time: "2019-03-02" },
      // { id: 2, num: "D19060123", fengmian: '../../images/lipins/lipin2.jpg', time: "2019-03-02" }
    ],
    //在仓的导航栏样式
    kong2: false
  },

  //待付款
  changeTab: function (e) {
    let index = e.currentTarget.dataset.index;
    this.setData({current: index, offset: 0});
    this.getData();
  },
  onLoad: async function (options) {
    this.setData({current: options.current || 1})
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
    let {offset,limit, current} = this.data;
    let con = {};
    if (current == 1) {
      con.pay_status = 0;
    } else if (current == 2) {
      con.pay_status = 2;
      con.ship_status = 0;
    } else if (current == 3) {
      con.pay_status = 2;
      con.ship_status = 1;
    } else if (current == 4) {
      con.pay_status = 2;
      con.ship_status = 2;
    }
    let res = await global.http.get('/api/bs/order/get/list', {...con,offset,limit});
    let loadList = res.data;
    loadList.forEach((order, i) => {
      order._order_no = order.order_no.substr(22,10);
      if ( order.picture) {
        order.pic = global.util.img(order.picture);
      }
      if (order.picture_logo) {
        order._picture_logo = global.util.img(order.picture_logo);
      }
      order.carrier_tracks_json = JSON.parse(order.carrier_tracks_json);
      order.specification = JSON.parse(order.specification);
      order.custom_made = JSON.parse(order.custom_made);
      order.user_address_json = JSON.parse(order.user_address_json);
      if (order.carrier_tracks_json) {
        try {
          order.carrier_tracks_json.carrier_tracks.forEach((item, i) => {
            item.accept_time = global.util.formatDate(item.accept_time);
          });

        } catch (e) {
          console.log(e);
        } finally {

        }
      }
    });

    if (offset != 0) {
      loadList = this.data.loadList.concat(loadList);
    }
    console.log(loadList);
    this.setData({hasMore: loadList.length < res.total, loadList});
    wx.hideLoading();
  },
  pay: async function (e) {
    let item = e.currentTarget.dataset.item;
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/order/pay`, {order_no: item.order_no, pay_channel: "YuE"});
    if (!res.code) {
      await global.util.showToast.message('支付成功');
      this.setData({offset: 0});
      this.getData();
    } else {
      global.util.showToast.hide();
      global.util.showToast.message(res.message);
    }
  },
  cancel: async function (e) {
    let item = e.currentTarget.dataset.item;
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/order/cancle/${item.order_no}`,);
    if (!res.code) {
      await global.util.showToast.message('取消成功');
      this.setData({offset: 0});
      this.getData();
    } else {
      global.util.showToast.hide();
      global.util.showToast.message(res.message);
    }
  },
  receive: async function (e) {
    let item = e.currentTarget.dataset.item;
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/order/update/shipStatus`, {order_no: item.order_no, ship_status: 2});
    if (!res.code) {
      await global.util.showToast.message('操作成功');
      this.setData({offset: 0});
      this.getData();
    } else {
      global.util.showToast.hide();
      global.util.showToast.message(res.message);
    }
  },
  loadDetails: async function(e) {
    let item = e.currentTarget.dataset.item;
    wx.setStorageSync("order", item);
    wx.navigateTo({url: '/pages/order_detail/index'});
  },

})
