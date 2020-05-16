<template>
  <div :style="'position:relative;height:500px;'+c_style">
    <div :style="`opacity:${loading?0:1}`" class="echart" ref="echart"></div>
    <a-skeleton v-if="loading" :style="`position:absolute;top:0;left:0;width:100%;height: 100%;`"></a-skeleton>
  </div>
</template>

<script>
// GMV近30天趋势	每天（不含今天）GVM	折线图 X:日期 Y：销售额（元）。同下图折柱混合
import echarts from "echarts";
export default {
  props: ["content", "query", "c_style"],
  watch: {
    content (newV) {
      this.init(newV);
      return newV;
    }
  },
  data () {
    return {
      loading: false,
    }
  },
  mounted () {
    if (this.query) {
      console.log(this.query);
      this.$e_on(this.query, (data) => {
        this.getAction(data)
      });
    }
    if (!this.echart) {
      this.echart = echarts.init(this.$refs.echart);
    }
    this.init(this.content);
  },
  methods: {
    init (content) {
      if (!content) return ;
      if (content.evalFunc) {
        eval(content.evalFunc);
      }
      if (this.echart && content && content.options) {
        this.setOption(content.options);
      }
      // NOTE: this.query 存在 即需要获取filter参数来getaction 故不初始化
      if (this.echart && content && content.actionId && !this.query) {
        this.getAction();
      }
    },
    getAction: async function (data={}) {
      let content = this.content;
      this.loading = true;
      let res = await this._http.post(`/api/${content.actionId}`, data);
      if (res.result) {
        this.setOption(res.result);
      } else {
        this.$message.error(res.message);
      }
      this.loading = false;
    },
    formatFunc (obj) {
      if (!obj) {
        return obj;
      } else if (obj instanceof Array) {
        obj.forEach(this.formatFunc)
      } else if (typeof obj == 'object') {
        Object.keys(obj).forEach(key => {
          if (typeof obj[key] == 'string' && obj[key].indexOf('function') != -1) {
            eval(`obj.${key} = ${obj[key]}`);
          } else {
            this.formatFunc(obj[key])
          }
        })
      }
    },
    setOption (options) {
      this.formatFunc(options);
      options.toolbox = options.toolbox || { feature: {saveAsImage: {name: options.save_name} }};
      if (options.xAxis) {
        options.xAxis.nameLocation = options.xAxis.nameLocation || 'center';
        options.xAxis.nameTextStyle = options.xAxis.nameTextStyle || { lineHeight: 30, };
      }

      this.echart && this.echart.setOption(options)
    }
  }
}
</script>

<style scoped lang="less">
.echart {
  width: 100%;
  height: 100%;
}
</style>
