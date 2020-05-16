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
      view: {}
    };
  },
  mounted: async function () {
    let res = await this._http.get(`/api/bs/gift/get/giftTypes`);
    let types = res.result;
    this.view = {
      title: "礼品类型管理",
      component: "Table",
      filters: [
        {name: "xxx", key: "xxx"}
      ],
      content: {
        btns: {
          component: "Button",
          content: {text:"新增礼品类型", link: "/manager/gift_new"}
        },
        dataSource: types,
        columns: [
          {dataIndex: "sort", title: "排序"},
          {dataIndex: "type_name", title: "名称"},
          {dataIndex: "type_code", title: "编码"},
          {dataIndex: "create_time", title: "创建时间"},
        ]
      }
    };
  },
  methods: {
  }
}
</script>

<style lang="less" scoped>
</style>
