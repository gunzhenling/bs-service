<template>
  <div class="layout" :class="{'average': content.average}" :style="content.style">
    <Container v-for="(child,index) in (content.children || content)" :key="index" :record="record" :body="body" :no_reload="no_reload" :view="child"/>
  </div>
</template>

<script>
export default {
  name: "Layout",
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
  },
  props: ["content", "record", "columns", "body", "no_reload", "c_style"],
  data () {
    console.log("------");
    return {
    }
  },
  methods: {
  }
}
</script>

<style scoped lang="less">
.layout {
  display: flex;
  flex-wrap: wrap;
  text-align: center;
  width: 100%;
}
.average {
  >* {
    flex: 1;
  }
}
</style>
