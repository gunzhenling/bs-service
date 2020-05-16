<template>
  <span v-if="self_content instanceof Array">
    <span v-for="(btn, index) in self_content" :key="index">
      <a-button :loading="loading" v-if="!btn.title" @click="click(btn)" v-bind="btn" style="margin-right: 10px;">
        {{btn.text}}
        <Modal v-bind="btn.modal"/>
        <!-- <a-modal v-if="btn.modal" v-model="visible" v-bind="btn.modal" :title="btn.modal.title || btn.text">
          <Container :view="btn.modal.content" :record="record" :column="column"/>
          <template slot="footer">
            <a-button :loading="loading" :type="btn.modal.cancelType || 'default'" @click="visible=false">{{btn.modal.cancelText || '取消'}}</a-button>
            <a-button :loading="loading" :type="btn.modal.cancelType || 'primary'" @click="handleOk(btn.modal)">{{btn.modal.onText || '确定'}}</a-button>
          </template>
        </a-modal> -->
      </a-button>
      <a-popconfirm v-else :placement="btn.placement || 'top'" :title="btn.title" :okText="btn.ok || '确定'" :cancelText="btn.cancel || '取消'" @confirm="click(btn)" style="margin-right: 10px;">
        <a-button :loading="loading" v-bind="btn">{{btn.text}}</a-button>
      </a-popconfirm>
    </span>
  </span>
  <span v-else>
    <a-button :loading="loading" v-bind="self_content" v-if="!self_content.title" @click="click(self_content)">
      {{self_content.text}}
      <Modal v-bind="self_content.modal"/>
      <!--
      <a-modal v-if="self_content.modal" v-model="visible" v-bind="self_content.modal" :title="self_content.modal.title || self_content.text">
        <Container :view="self_content.modal.content" :record="record" :column="column"/>
        <template slot="footer">
          <a-button :loading="loading" :type="self_content.modal.cancelType || 'default'" @click="visible=false">{{self_content.modal.cancelText || '取消'}}</a-button>
          <a-button :loading="loading" :type="self_content.modal.cancelType || 'primary'" @click="handleOk(self_content.modal)">{{self_content.modal.onText || '确定'}}</a-button>
        </template>
      </a-modal> -->
    </a-button>
    <a-popconfirm v-else :placement="self_content.placement || 'top'" :title="self_content.title" :okText="self_content.ok || '确定'" :cancelText="self_content.cancel || '取消'" @confirm="click(self_content)">
      <a-button :loading="loading" v-bind="self_content">{{self_content.text}}</a-button>
    </a-popconfirm>
  </span>
</template>

<script>
export default {
  props: ["content", "record", "column"],
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
    this.$options.components.Modal = () => import('./Modal.vue')
  },
  watch: {
    // content (content) {
    //   console.log(this.bindFunc(content));
    //   return this.bindFunc(content);
    // }
  },
  mounted () {
    console.log(this.content);
    this.self_content = this.bindFunc(this.content);
  },
  data () {
    return {
      visible: false,
      loading: false,
      self_content: {},
    }
  },
  methods: {
    click: async function (button) {
      if (button.click) {
        if (typeof button.click == 'string') {
          eval(`button.click = ${button.click} `);
        }
        this.loading = true;
        await button.click(this.record, this.column, this.content);
        this.loading = false;
        return ;
      }
      // NOTE: 跳转url
      if (button.link) {
        if (button.link.indexOf('/') == 0) {
          this.$router.push(button.link);
        } else {
          location.href = button.link;
        }
        return ;
      } else if (button.open) {
        window.open(button.open);
        return ;
      } else if (button.postId) {
        this.loading = true;
        let postId =  typeof button.postId == 'string' ? button.postId : button.postId(this.record);
        let res = await this._http.post(`/api/${postId}`,{record: this.record, ...(button.body || {})}).then(res => {
          if (res.code) {
            if (button.cb) {
              if (typeof button.cb == 'string') {
                eval(`button.cb = ${button.cb} `);
              }
              button.cb(res);
            } else if (button.no_reload) {// NOTE: 不刷新只提交reload事件，给表格刷新
              this.$emit('reload');
            } else {
              this.$e_emit('page_reload');
            }
          } else {
            this.$message.error((res.result && res.result.message) || res.message);
          }
          this.loading = false;
        }, err => {
          this.$message.error((res.result && res.result.message) || res.message);
          this.loading = false
        });
        return ;
      }
      button.visible = true;
    },
    handleOk: async function (modal) {
      this.loading = true;
      if (modal.click) {
        if (typeof modal.click == 'string') {
          eval(`modal.click = ${modal.click} `);
        }
        await modal.click(this.record, this.column, this.content);
        this.loading = false;
        return ;
      }
      let res = await this.confirmOk(modal);
      this.loading = false;
      if (res && res.code) {
        this.$message.error(res.message);
      } else {
        this.$message.success(res.message);
        if (modal.no_reload) {// NOTE: 不刷新只提交reload事件，给表格刷新
          this.$emit('reload');
        } else {
          this.$e_emit('page_reload');
        }
      }

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
