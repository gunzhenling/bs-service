<template>
  <!-- NOTE 不一样的dom元素，达到刷新效果 -->
  <div v-if="n_view">
    <Container style="text-align: left;" :view="n_view"/>
  </div>
  <Container style="text-align: left;" v-else="view" :view="view"/>
</template>

<script>
import Container from "../../components/Container.vue";
export default {
  props: ["view"],
  components: {Container},
  data () {
    return {
      n_view: ''
    };
  },
  mounted: async function () {
    this.$e_on('page_reload', async () => {
      this.n_view = this.n_view ? '' : (
        typeof this.view == 'object' ? {...this.view} : this.view
      );
    })
  }
}
</script>

<style lang="css" scoped>
</style>
