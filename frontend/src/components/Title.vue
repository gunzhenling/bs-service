<template>
  <div class="title">{{content}}</div>
</template>

<script>
export default {
  props: ["content"],
  data () {
    return {
    }
  },
}
</script>

<style scoped lang="less">

.title {
  font-size: 24px;
  font-weight: bold;
  text-align: left;
  margin: 20px 0;
  position: relative;
  &::after {
    display: block;
    content: " ";
    width: 5px;
    height: 80%;
    position: absolute;
    top: 10%;
    left: -10px;
    background: black;
    border-radius: 4px 0 0 4px;
  }
}
</style>
