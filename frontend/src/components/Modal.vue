<template>
  <a-modal v-if="content" v-bind="content" v-model="content.visible">
    <Container v-if="content.content" :view="content.content" :record="record" :column="column"/>
    <template v-if="!content.btns" slot="footer">
      <a-button :loading="content.loading" :type="content.cancelType || 'default'" @click="content.visible=false">{{content.cancelText || '取消'}}</a-button>
      <a-button :loading="content.loading" :type="content.cancelType || 'primary'" @click="handleOk()">{{content.onText || '确定'}}</a-button>
    </template>
    <Container v-else :view="content.btns" :record="record" :column="column"/>
  </a-modal>
</template>

<script>
export default {
  props: ["content", "record", "column"],
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
  },
  watch: {
    content (newV) {
      newV.loading = newV.loading || false;
      newV.visible = newV.visible || false;
      return newV;
    }
  },
  data () {
    return {
    }
  },
  methods: {
    handleOk: async function () {
      let {content} = this;
      content.loading = true;
      let res = await this.confirmOk(content);
      content.loading = false;
      // NOTE: 没有返回信息，即不是接口，可能是一些操作事件，不reload和toast
      if (!res) {
        return ;
      }
      if (res && res.code) {
        this.$message.error(res.message);
      } else {
        this.$message.success(res.message);
        if (content.no_reload) {// NOTE: 不刷新只提交reload事件，给表格刷新
          this.$emit('reload');
        } else {
          this.$e_emit('page_reload');
        }
      }
    },
    confirmOk: async function(modal) {
      // if (!this.data.actionId) {
      //   throw "必须要参数actionId"
      // }
      let data = {};
      const regxForm = (arr) => {
        let success = true;
        arr.forEach(e => {
          // NOTE: 图片
          if (e.type == "image") {
            let images = [];
            images = e.value.map((image, err) => {
              if (image.response && image.response.url) {
                return image.response.url;
              } else if (image.url) {
                return image.url;
              } else {
                this.$message.error("请等待图片上传成功");
                success = false;
              }
            });
            // NOTE: 一张图片，单张上传
            if (!e.limit || e.limit == 1) {
              images = images[0]
            }
            data[e.key] = images;
          } else {
            data[e.key] = e.value;
          }
        });
        return success;
      }
      if (modal.content instanceof Array) {
        for (var i = 0; i < modal.content.length; i++) {
          if (modal.content[i].component == "Form") {
            if (!regxForm(modal.content[i].content)) return ;
          }
        }
      } else {
        if (!regxForm(modal.content.content)) return ;
      }
      let res;

      if (modal.click) {
        if (typeof modal.click == 'string') {
          eval(`modal.click = ${modal.click} `);
        }
        res = await modal.click({record: this.record, ...(modal.body || {}), ...data});
      } else if (modal.postId instanceof Function) {
        res = await modal.postId({record: this.record, ...(modal.body || {}), ...data});
      } else {
        res = await this._http.post(`/api/${modal.postId}`, {record: this.record, ...(modal.body || {}), ...data});
      }
      return res;
    }
  }
}
/* // NOTE: component
content:
  link 原页面跳转url
  open 新开页面跳转url
  text 按钮文案
*/
</script>
<style>
</style>
