<template>
  <div style="position:relative;">
    <div v-html="html" :style="`opacity:${loading?0:1}`"> </div>
    <a-skeleton v-if="loading" :style="`position:absolute;top:0;left:0;width:100%;height: 100%;`"></a-skeleton>
    <Container v-if="container" :record="record" :view="container"/>
  </div>
</template>

<script>
export default {
  props: ["content", "record", "column", "query"],
  watch: {
    content (newV) {
      this.initData(newV);
      return newV;
    },
    record (newV) {
      this.initData(this.content);
      return newV;
    },
  },
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
  },
  mounted () {
    this.initData(this.content);
    if (this.query) {
      this.$e_on(this.query, (data) => {
        console.log(data);
        this.getAction(data)
      });
    }
  },
  data () {
    return {
      html: "",
      container: "",
      loading: false,
    }
  },
  methods: {
    initData (content) {
      if (!content) {
        if (this.record) {
          if (this.column.evalFunc) {
            eval(this.column.evalFunc);
          }
          this.html = eval('`' + this.record[this.column.dataIndex] + '`');
        }
        if (content && content.actionId && !this.query) {
          this.getAction({});
        }
      } else {
        if (content.evalFunc) {
          eval(content.evalFunc);
        }
        // NOTE: 解析部分数据
        this.html = eval('`' + (content.html || content) + '`');
      }
    },
    getAction: async function (data) {
      this.loading = true;
      let res = await this._http.post(`/api/${this.content.actionId}`, data);
      this.html = '';
      this.container = '';
      if (res.result) {
        // NOTE: 对象交给container解析
        if (typeof res.result == 'object') {
          this.container = res.result;
        } else {
          this.html = eval('`' + res.result + '`');
        }
      } else {
        this.$message.error(res.message);
      }
      this.loading = false;
    }
  }
}
</script>

<style>
</style>
