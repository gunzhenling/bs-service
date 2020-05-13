// pages/category/category.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    select: 0,
    categories: [
      {
        id: 1,
        name: '全部'
      },
      {
        id: 2,
        name: '爱心助农'
      },
      {
        id: 3,
        name: '小心意'
      },
      {
        id: 4,
        name: '感谢'
      },
      {
        id: 5,
        name: '礼盒'
      },
      {
        id: 6,
        name: '生日'
      },
      {
        id: 7,
        name: '结婚'
      },
      {
        id: 8,
        name: '商务'
      },
      {
        id: 9,
        name: '乔迁'
      },
      {
        id: 10,
        name: '生子'
      },
      {
        id: 11,
        name: '美食'
      },
      {
        id: 12,
        name: '美妆'
      },
      {
        id: 13,
        name: '数码'
      },
      {
        id: 14,
        name: '家电'
      },
      {
        id: 15,
        name: '家居'
      }
    ],
    goodsList: [
      {
        id: 1,
        title: "手机LED补光灯 手机拍照补光灯、闪光灯 自拍神器 10元以下小礼品",
        image: "../../images/lipins/lipin1.jpg",
        price: 8.80
      },
      {
        id: 2,
        title: "VR眼镜手机科幻游戏  虚拟现实眼镜 多功能3D家庭影院  ",
        image: "../../images/lipins/lipin2.jpg",
        price: 18.80
      },
      {
        id: 3,
        title: "吃鸡耳机头戴式 电脑电竞游戏绝地求生带麦",
        image: "../../images/lipins/lipin3.jpg",
        price: 6.80
      },
      {
        id: 4,
        title: "复古迷你掌上游戏机3.0英寸大屏 内置168款游戏 怀旧掌机",
        image: "../../images/lipins/lipin4.jpg",
        price: 8.80
      }
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
  /**
   * 选择商品类型
   */ 
  categoryClick: function(event) {
    this.setData({
      select: event.target.id - 1
    })
    // toast提示
    wx.showToast({
      title: event.target.id,
    })
    // 更新接口
  },
  /**
   * 加入购物车
   */
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