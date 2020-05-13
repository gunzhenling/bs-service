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
      view: {
        title: "礼品管理",
        component: "Table",
        filters: [
          {name: "xxx", key: "xxx"}
        ],
        content: {
          dataSource: [{}],
          columns: [
            {dataIndex: "order_no", title: "订单编号"},
            {dataIndex: "gift_code", title: "礼品编号"},
            {dataIndex: "gift_name", title: "礼品名称"},
            {dataIndex: "gift_amount", title: "购买数量"},
            {dataIndex: "specification", title: "规格"},
            {dataIndex: "custom_made", title: "定制"},
            {dataIndex: "status", title: "状态"},
          ]
        }
      }
    };
  },
  mounted () {
  },
  methods: {
  }
}
</script>

<style lang="less" scoped>
</style>
