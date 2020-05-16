<template>
  <div class="">
    <Container v-if="view" :view="view"/>
  </div>
</template>

<script>
import Container from "../../components/Container.vue";
export default {
  components: {Container},
  data () {
    // 函数编号	功能函数名	父级标签	访问级别	函数类型	函数状态	操作
    return {
      view: ""
    };
  },
  mounted: async function () {
    let res = await this._http.get(`/api/bs/gift/get/giftTypes`);
    let types = res.result;
    this.view = {
      title: "创建礼品",
      component: "Form",
      content: [
        {key: "gift_code", name: "礼品编码", style:"max-width:200px"},
        {key: "gift_name", name: "礼品名称", style:"max-width:200px"},
        {key: "gift_price", name: "原始价格", type: "number"},
        {key: "real_gift_price", name: "实际价格", type: "number"},
        {key: "type_code", name: "礼品分类", type: "selector", options: types.map(e => ({
          name: e.type_name,value: e.type_code
        }))},
        {key: "sale_num", name: "已售数量", type: "number"},
        {key: "limit_num", name: "定制最少数量", type: "number"},
        {key: "picture_file", name: "图片", style:"max-width:200px"},
        {key: "content", name: "礼品详情", type: "editor"},
      ]
    }
    console.log(res);


  },
  methods: {
  }
}
</script>

<style lang="less" scoped>
</style>
