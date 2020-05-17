// pages/noticelist/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    offset: 0,
    limit: 10,
    hasMore: true,
    info: [
      {
        id: 0, name: "服务器升级公告", content: "亲爱的用户：因数据中心割接，从即日起到 1 月 9 日中午 12 点，我们将对服务器做一些优化和升级，本次服务器升级涉及范围广，因此会对功能有较大影响，在此期间我们将暂停部分功能，在此期间，您仍可使用搜索、浏览等功能。我们有完善的数据安全措施，您的图片等数据不会受到任何影响，请放心。抱歉给您带来不便。", time: "2019-01-03 15:00:34", notice: true,
      },
      {
        id: 1, name: "服务器升级公告", content: "亲爱的用户：因数据中心割接，从即日起到 1 月 9 日中午 12 点，我们将对服务器做一些优化和升级，本次服务器升级涉及范围广，因此会对功能有较大影响，在此期间我们将暂停部分功能，在此期间，您仍可使用搜索、浏览等功能。我们有完善的数据安全措施，您的图片等数据不会受到任何影响，请放心。抱歉给您带来不便。", time: "2019-01-03 15:00:34", notice: true,
      },
    ],
    display: "fixed"
  },
  getData: async function () {
    wx.showLoading();
    let {offset,limit} = this.data;
    let res = await global.http.get('/api/bs/common/notice/get/list', {made_type:0,offset,limit});
    let info = res.data;
    info.forEach((item, i) => {
      item.notice = false;
    });

    if (offset != 0) {
      info = this.data.info.concat(info);
    }
    this.setData({hasMore: info.length < res.total, info});
    wx.hideLoading();
  },
  //服务器升级消息
  notice(e) {
    var index = e.currentTarget.dataset.index;
    this.data.info[index].notice = !this.data.info[index].notice;
    this.setData({info: [...this.data.info]})
  },
  //返回
  goBack(){
    wx.navigateBack();
  },
  onHide() {
    var infos = this.data.info
    for (i in infos) {
      infos[i].notice = true
    }
    this.setData({
      info: infos
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData();
  },
})
