<template>
  <div>
    <a-steps progressDot :current="content.current">
      <a-step v-for="(item,index) in content.steps" :key="index" :title="item.title" :description="item.description"></a-step>
    </a-steps>
  </div>
</template>

<script>
export default {
  name: "Steps",
  props: ["content", "record", "columns"],
  data () {
    console.log(this.content);
    return {
    }
  },
  methods: {
  }
}
</script>

<style scoped lang="less">
</style>
