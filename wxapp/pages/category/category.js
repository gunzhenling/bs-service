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
    this.setData({ select: event.target.id, offset: 0 });
    this.getData();
    // 更新接口
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit, select, categories} = this.data;
    let res = await global.http.get('/api/bs/gift/get/list', {offset,limit, type_code: categories[select].type_code});
    let goodsList = res.data;
    goodsList.forEach((item, i) => {
      let pic = item.picture.split("\\");
      item.pic = "http://localhost:5000/" + pic[pic.length-1].replace("bs-service/frontend/public", "");
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
  addToCart: function (event) {
    // toast提示
    wx.showToast({
      title: '加入成功！',
    })
    // console.log(event.target.id)
    // console.log(event.target.id.split(','))
    // this.setData({
    //   showImage: true,
    //   imageUrl: event.target.id.split(',')[0],
    //   btnId: event.target.id.split(',')[1]
    // })
    // console.log('imageUrl', this.data.imageUrl)
    // var _this=this;
    // /*创建节点选择器*/
    // var query = wx.createSelectorQuery();
    // /*选择id*/
    // query.select('#goods-image-' + this.data.btnId).boundingClientRect()
    // query.exec(function (res) {
    //   /*res就是该元素的信息数组*/
    //   console.log(res);
    //   /*获取节点信息*/
    //   _this.setData({
    //     imageWidth: res[0].width,
    //     imageHeight: res[0].height,
    //     imageX: res[0].left,
    //     imageY: res[0].top,
    //     imageStyle: 'width:'+res[0].width + "px;" +
    //                 'height:' + res[0].height + 'px;' +
    //                 'position: fixed;' +
    //                 'z-index: 100;' +
    //                 'left: ' + res[0].left + 'px;' +
    //                 'top: ' + res[0].top + 'px;' +
    //                 'animation: drop 5s ease .5s;' +
    //       '@-webkit-keyframes drop {\n' +
    //     '  0% {\n' +
    //     '    width: 60px;\n' +
    //     '    height: 60px;\n' +
    //     '  }\n' +
    //     '  100% {\n' +
    //     '    width: 1.15rem;\n' +
    //     '    height: 1.15rem;\n' +
    //     '    left: 200px;\n' +
    //     '    top: 700px;\n' +
    //     '    border-radius: 50%;\n' +
    //     '    -webkit-transform: rotate(180deg);\n' +
    //     '    -moz-transform: rotate(180deg);\n' +
    //     '    -o-transform: rotate(180deg);\n' +
    //     '    -ms-transform: rotate(180deg);\n' +
    //     '  }\n' +
    //     '}'
    //
    //   })
    //   console.log('取坐标X', _this.data.imageX);
    //   console.log('取坐标Y', _this.data.imageY);
    //   /*动态渲染商品图片，用以动画效果*/
    //   setTimeout(function () {
    //     _this.setData({
    //       showImage: false
    //     })
    //   }, 3000)
    // })
  }
})
