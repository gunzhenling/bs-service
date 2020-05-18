// pages/category/category.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    offset: 0,
    limit: 10,
    hasMore: true,

    gift: {},
    showModal: false,
    selected: "",
    buy_num: 1,
    buy_type: 0,

    select: 0,
    categories: [ ],
    goodsList: [
    ],
    imageWidth: "",
    imageHeight: "",
    imageX: "",
    imageY: "",
    wxAppendData: [],
    imageUrl: '',
    btnId: '',
    showImage: false,
    imageStyle: ''

  },
  onLoad: async function () {
    wx.showLoading();
    let categories = await global.http.get('/api/bs/gift/get/giftTypes');
    this.setData({categories});
    wx.hideLoading();
    this.getData();
  },
  onReachBottom: async function () {
    let {limit,hasMore,offset} = this.data;
    if (!hasMore) return ;
    this.setData({offset: offset+limit});
    this.getData();
  },
  onHide () {
    this.setData({showModal: false});
  },
  /**
   * 选择商品类型
   */
  categoryClick: function(event) {
    console.log(event);
    this.setData({ select: event.target.id, offset: 0, searchText: '' });
    this.getData();
    // 更新接口
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit, select, searchText, categories} = this.data;
    let con = {offset,limit,made_type: 0};
    if (!searchText) {
      con.type_code = categories[select].type_code;
    } else {
      con.search_query = searchText;
    }
    let res = await global.http.get(`/api/bs/gift/${searchText?"search":"get/list"}`, con);
    let goodsList = res.data;
    goodsList.forEach((item, i) => {
      item.pic = global.util.img(item.picture);
      item.specification = JSON.parse(item.specification);
      item.custom_made = JSON.parse(item.custom_made);
      // wx.getLocation({success: e => {
      //   console.log(e);
      // }})
    });

    if (offset != 0) {
      goodsList = this.data.goodsList.concat(goodsList);
    }
    this.setData({hasMore: goodsList.length < res.total, goodsList});
    wx.hideLoading();
  },
  toggle (e) {
    if (!this.data.showModal && e.currentTarget) {
      let gift = e.currentTarget.dataset.gift;
      this.setData({gift: gift, selected: gift.specification[0].standards, s_custom_made: 0});
    }
    this.setData({showModal: !this.data.showModal});
  },
  clickItem(e){
    console.log(e);
    this.setData({selected: e.currentTarget.dataset.item.standards});
  },
  clickCustom(e){
    console.log(e);
    this.setData({s_custom_made: e.currentTarget.dataset.index});
  },
  /**
   * 加入购物车
   */
 tocar: async function () {
   let {gift,selected,buy_num, s_custom_made} = this.data;
   gift.buy_num = buy_num;
   gift.standards = selected;
   let res = await global.http.post("/api/bs/order/add/shops", {
     gift_code: gift.gift_code,
     gift_amount: gift.buy_num,
     picture_url: gift.picture,
     sell_income: Number((gift.gift_price*gift.buy_num).toFixed(2)),
     buyer_pay_amount: Number((gift.real_gift_price*gift.buy_num).toFixed(2)),
     specification: {standards:gift.standards},
     custom_made: gift.custom_made[s_custom_made],
   });
   console.log(res);
   wx.setStorageSync('gift', gift);
   wx.showToast({ title: '加入成功！', })
   this.toggle();
 },
  search: function (e) {
    console.log(e);
    this.setData({select: 9999, searchText: e.detail.value});
    this.getData();
  }
})
