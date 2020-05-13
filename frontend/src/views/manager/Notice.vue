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
        title: "公告管理",
        component: "Table",
        filters: [
          {name: "xxx", key: "xxx"}
        ],
        content: {
          btns: {
            component: "a-button",
            text: "新增公告",
            modal: {
              visible:true,
              width: '700px',
              content: {
                component: "Form",
                content: [
                  {key: "gift_code", name: "礼品编码", style:"max-width:200px"},
                  {key: "gift_name", name: "礼品名称", style:"max-width:200px"},
                  {key: "gift_price", name: "原始价格", type: "number"},
                  {key: "real_gift_price", name: "实际价格", type: "number"},
                  {key: "type_code", name: "实际价格", type: "selector", options: [
                    {name:111,value:"!1"},
                    {name:222,value:"!2"},
                  ]},
                  {key: "sale_num", name: "已售数量", type: "number"},
                  {key: "limit_num", name: "定制最少数量", type: "number"},
                  {key: "picture_file", name: "图片", style:"max-width:200px"},
                  {key: "content", name: "礼品详情", type: "editor"},
                ]
              }
              }
          },
          dataSource: [{}],
          columns: [
            {dataIndex: "title", title: "标题"},
            {dataIndex: "content", title: "内容", type: "Html"},
            {dataIndex: "create_time", title: "创建时间"},
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
