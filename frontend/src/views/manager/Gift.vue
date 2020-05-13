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
          btns: {
            component: "Button",
            content: {text:"新增礼品", link: "/manager/gift_new"}
          },
          dataSource: [{}],
          columns: [
            {dataIndex: "gift_name", title: "礼品名称"},
            {dataIndex: "gift_price", title: "原始价格"},
            {dataIndex: "real_gift_price", title: "实际价格"},
            {dataIndex: "sale_num", title: "已售数量"},
            {dataIndex: "limit_num", title: "定制至少售卖数量"},
            {dataIndex: "picture", title: "图片", type: 'img'},
            {dataIndex: "status", title: "状态", content: {
              component: "a-switch",
              postId: "xxx"
            }},
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
