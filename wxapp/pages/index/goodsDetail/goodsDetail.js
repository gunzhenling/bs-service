Page({
  data: {
    gift: {},
    showModal: false,
    selected: "",
    buy_num: 1,
    buy_type: 0,

    dz: '',

    select: 1,//tab默认选中第一个
    detailSelect: 1,//商品详情tab默认选中第一个
    slides: [
      "//img12.360buyimg.com/babel/s340x420_jfs/t20371/32/1913614468/53754/b55c71b2/5b3c4049N3ceedd04.jpg!q90!cc_340x420",
      "//img12.360buyimg.com/babel/s340x420_jfs/t21397/84/1924713889/108707/a87570bf/5b3ec5bbNcec385d6.jpg!q90!cc_340x420",
      "//img14.360buyimg.com/babel/s340x420_jfs/t21880/97/1771609351/101047/ebdd4882/5b336aa6Nf24d6674.jpg!q90!cc_340x420"
    ],
    tabs: [
      {
        id: 1,
        name: "评论"
      },
      {
        id: 2,
        name: "推荐"
      },
      {
        id: 3,
        name: "详情"
      }
   ],
    commentList: [
      {
        id: 1,
        avatar: "//img12.360buyimg.com/babel/s340x420_jfs/t20371/32/1913614468/53754/b55c71b2/5b3c4049N3ceedd04.jpg!q90!cc_340x420",
        nickname: "詹姆斯",
        comment: "刚送到就迫不及待地打开试了下，外观挺漂亮的，吹出的风比较温和，今天的室内温度有30度，加上水用着还行，不知再热点加冰效果如何。整体来说还行，用一段时间再点评。"
      },
      {
        id: 2,
        avatar: "//img12.360buyimg.com/babel/s340x420_jfs/t20371/32/1913614468/53754/b55c71b2/5b3c4049N3ceedd04.jpg!q90!cc_340x420",
        nickname: "詹姆斯",
        comment: "刚送到就迫不及待地打开试了下，外观挺漂亮的，吹出的风比较温和，今天的室内温度有30度，加上水用着还行，不知再热点加冰效果如何。整体来说还行，用一段时间再点评。"
      },
      {
        id: 2,
        avatar: "//img12.360buyimg.com/babel/s340x420_jfs/t20371/32/1913614468/53754/b55c71b2/5b3c4049N3ceedd04.jpg!q90!cc_340x420",
        nickname: "詹姆斯",
        comment: "刚送到就迫不及待地打开试了下，外观挺漂亮的，吹出的风比较温和，今天的室内温度有30度，加上水用着还行，不知再热点加冰效果如何。整体来说还行，用一段时间再点评。"
      },
      {
        id: 2,
        avatar: "//img12.360buyimg.com/babel/s340x420_jfs/t20371/32/1913614468/53754/b55c71b2/5b3c4049N3ceedd04.jpg!q90!cc_340x420",
        nickname: "詹姆斯",
        comment: "刚送到就迫不及待地打开试了下，外观挺漂亮的，吹出的风比较温和，今天的室内温度有30度，加上水用着还行，不知再热点加冰效果如何。整体来说还行，用一段时间再点评。"
      }
    ],
    recommendList: [
      {
        id: 1,
        title: "风靡ins的伦敦腕风靡ins的伦敦腕",
        img: "//img13.360buyimg.com/n1/jfs/t20062/238/2403877143/153179/a734465b/5b46bb72Nab9a6ea7.jpg",
        price: "399",
        originalPrice: "488"
      },
      {
        id: 2,
        title: "京觅 新疆库尔勒香梨 一级中果 总重约2kg 新鲜水果",
        img: "//img12.360buyimg.com/mobilecms/s500x500_jfs/t17515/256/1947534381/244203/f54cd223/5adef415Nf7a0a895.jpg",
        price: "399",
        originalPrice: "488"
      },
      {
        id: 3,
        title: "印尼进口 Nabati 丽芝士（Richeese）休闲零食 奶酪味 威化饼干 460g/盒 早餐下午茶",
        img: "//img13.360buyimg.com/n1/jfs/t17941/226/2669737138/420972/35402273/5b062750N2e8adbff.jpg",
        price: "399",
        originalPrice: "488"
      },
      {
        id: 4,
        title: "联想(Lenovo)小新潮7000 14英寸轻薄笔记本电脑(英特尔八代酷睿I5-8250U 8G 2T+128G R535 office2016)",
        img: "//img20.360buyimg.com/mobilecms/s700x256_jfs/t7393/171/765889073/61915/936cc270/5996e7eaNa22d72b4.jpg!q90!cc_350x128.webp",
        price: "399",
        originalPrice: "488"
      },
      {
        id: 5,
        title: "联想(Lenovo)小新潮7000 14英寸轻薄笔记本电脑(英特尔八代酷睿I5-8250U 8G 2T+128G R535 office2016)",
        img: "//img11.360buyimg.com/mobilecms/s700x256_jfs/t20539/229/82605291/66559/df347eb5/5af96a18N9451b1a1.jpg!q90!cc_350x128.webp",
        price: "399",
        originalPrice: "488"
      },
      {
        id: 6,
        title: "联想(Lenovo)小新潮7000 14英寸轻薄笔记本电脑(英特尔八代酷睿I5-8250U 8G 2T+128G R535 office2016)",
        img: "//img20.360buyimg.com/mobilecms/s700x256_jfs/t7393/171/765889073/61915/936cc270/5996e7eaNa22d72b4.jpg!q90!cc_350x128.webp",
        price: "399",
        originalPrice: "488"
      }
    ],
    detailTabs: [
      {
      id: 1,
      name: "商品介绍"
      },
      // {
      //   id: 2,
      //   name: "咨询与售后"
      // }
    ]
  },
  onLoad: async function (options) {
    this.setData({dz: options.dz || ''});
    wx.showLoading();
    let gift = wx.getStorageSync('gift');
    let res = await global.http.get(`/api/bs/gift/get/detail?gift_code=${gift.gift_code}`);
    let pic = res.picture.split("\\");
    res.pic = "http://localhost:5000/" + pic[pic.length-1].replace("bs-service/frontend/public", "");
    console.log(res);
    res.specification = JSON.parse(res.specification);
    res.custom_made = JSON.parse(res.custom_made);
    this.setData({gift: res, selected: res.specification[0].standards, s_custom_made: 0});
    wx.hideLoading();
  },
  toggle (e) {
    if (!this.data.showModal && e.currentTarget) {
      this.setData({buy_type: e.currentTarget.dataset.type});
    }
    this.setData({showModal: !this.data.showModal});
  },
  preventScroll(){},
  clickItem(e){
    console.log(e);
    this.setData({selected: e.currentTarget.dataset.item.standards});
  },
  clickCustom(e){
    console.log(e);
    this.setData({s_custom_made: e.currentTarget.dataset.index});
  },
  c_buy_num (e) {
    let {buy_num} = this.data;
    if (e.currentTarget.dataset.num) {
      buy_num += Number(e.currentTarget.dataset.num);
      if (buy_num <= 0) buy_num = 1;
    } else {
      buy_num = Number(e.detail.value) || 1;
    }
    this.setData({buy_num})
  },
  buy () {
    let {gift,selected,buy_num, s_custom_made} = this.data;
    gift.buy_num = buy_num;
    gift.buyer_pay_amount = Number((gift.real_gift_price*gift.buy_num).toFixed(2));
    gift.sell_income = Number((gift.gift_price*gift.buy_num).toFixed(2));
    gift.specification = {standards: selected};
    gift.custom_made = gift.custom_made[s_custom_made];
    wx.setStorageSync('gift', gift);
    wx.navigateTo({url: "/pages/orderon/index"})
  },
  tocar: async function () {
    let {gift,selected,buy_num, s_custom_made} = this.data;
    gift.buy_num = buy_num;
    gift.buyer_pay_amount = Number((gift.real_gift_price*gift.buy_num).toFixed(2));
    gift.sell_income = Number((gift.gift_price*gift.buy_num).toFixed(2));
    gift.specification = {standards: selected};
    let res = await global.http.post("/api/bs/order/add/shops", {
      gift_code: gift.gift_code,
      gift_amount: gift.buy_num,
      sell_income: gift.sell_income,
      buyer_pay_amount: gift.buyer_pay_amount,
      picture: gift.picture,
      specification: gift.specification,
      custom_made: gift.custom_made[s_custom_made],
    });
    wx.setStorageSync('gift', gift);
    wx.showToast({ title: '加入成功！', })
    this.toggle();
  },
  /*返回上一页*/
  goBack: function() {
    console.log('哈哈')
    wx.navigateBack()
  },
  /*点击tab切换*/
  selectTab: function (event) {
    this.setData({
      select: event.currentTarget.id
    })
  },
  /*商品详情tab切换*/
  selectDetailTab: function (event) {
    this.setData({
      detailSelect: event.currentTarget.id
    })
  },
  clickLike: async function () {
    let {gift} = this.data;
    let like = gift.like;
    global.util.showToast.loading();
    let res = await global.http.post(`/api/bs/user/giftLike/${!like ? 'add' : `cancle/${like}`}`, {gift_code:gift.gift_code});
    global.util.showToast.hide();
    if (!res.code) {
      global.util.showToast.message(!like ? "收藏成功" : "取消收藏");
    } else {
      global.util.showToast.message(res.message);
    }
  },
  clickCar () {
    wx.switchTab({url: `/pages/cart/cart`})
  }
})
