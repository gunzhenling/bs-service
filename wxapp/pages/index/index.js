//index.js

Page({
  data: {
    slides: [
      "../../images/slides/banner1.jpg",
      "../../images/slides/banner2.jpg",
      "../../images/slides/banner3.jpg"
    ],
    bargainList: [
      {
        title: "手机LED补光灯 手机拍照补光灯、闪光灯 自拍神器 10元以下小礼品",
        img: "../../images/lipins/lipin1.jpg",
        desc: "尺寸38*38*10 mm，材质：电子元件",
        basePrice: "50",
        originalPrice: "120",
        limit: "18"
      },
      {
        title: "VR眼镜手机科幻游戏  虚拟现实眼镜 多功能3D家庭影院  汽车赠品有哪些  公司年会奖品设置",
        img: "../../images/lipins/lipin2.jpg",
        desc: "尺寸222*205*99mm，材质：黑色",
        basePrice: "3999",
        originalPrice: "48888",
        limit: "18"
      },
      {
        title: "吃鸡耳机头戴式 电脑电竞游戏绝地求生带麦  联想笔记本电脑促销活动 比赛 奖品",
        img: "../../images/lipins/lipin3.jpg",
        desc: "尺寸252*259*150mm，材质：银黑色",
        basePrice: "3999",
        originalPrice: "48888",
        limit: "18"
      },
      {
        title: "复古迷你掌上游戏机3.0英寸大屏 内置168款游戏 怀旧掌机 超级玛丽俄罗斯方块 减压小礼品",
        img: "../../images/lipins/lipin4.jpg",
        desc: "颜色混色随机",
        basePrice: "75",
        originalPrice: "127",
        limit: "289"
      }
    ],
    topicList: [
      {
        img: "../../images/tuijians/tuijian1.png",
        title: "加湿净化小礼品市集",
        subTitle: "舒适健康的办公环境是非常重要的，尤其在多人的办公室里面...",
        price: "91"
      },
      {
        img: "../../images/tuijians/tuijian2.png",
        title: "厨房创意实用帮手小礼品",
        subTitle: "厨房在一套房子里面的功能，不用说，就是料理家人...",
        price: "23"
      },
      {
        img: "../../images/tuijians/tuijian3.png",
        title: "提升家里蹲幸福感的好玩礼品",
        subTitle: "小集今天来为各位小伙伴们推荐提升家...",
        price: "129"
      }
    ]

  },
  //跳转定制
  goDingZhiList:function(){
    wx.navigateTo({
      url: '../../pages/dingzhilist/index',
    })
  },
  //跳转公告
  goNoticeList:function(){
    wx.navigateTo({
      url: '../noticelist/index',
    })
  },
  //跳转操作指南
  goHelpList:function(){
    wx.navigateTo({
      url: '../helplist/index',
    })
  },
  goDetail: function (event) {
    console.log(event)
    wx.navigateTo({
      url: './goodsDetail/goodsDetail'
    })
  }
})