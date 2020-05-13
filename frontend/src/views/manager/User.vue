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
        title: "用户列表",
        component: "Table",
        filters: [
          {name: "xxx", key: "xxx"}
        ],
        content: {
          dataSource: [{}],
          columns: [
            {dataIndex: "name", title: "用户名"},
            {dataIndex: "icon", title: "头像", type:"image"},
            {dataIndex: "create_time", title: "注册时间"},
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
