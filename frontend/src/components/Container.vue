<template>
  <div>
  <!-- <div style="width:100%;"> -->
    <a-skeleton v-if="typeof view === 'string' || typeof view === 'number'" v-show="Boolean(loading)"></a-skeleton>
    <!-- NOTE: 自定义组件才有title (除了Button)-->
    <Title v-if="data.title && (data.component == 'Html' || data.component == 'Layout' || data.component == 'Echart' || data.component == 'Table'|| data.component == 'Filters'|| data.component == 'Form')" :content="data.title"/>
    <!-- NOTE: 自定义组件才有 html -->
    <div v-if="data.html" v-html="data.html" > </div>
    <a-tooltip v-if="data.tip" placement="top" :title="data.tip.text || data.tip" :getPopupContainer="e => e.parentElement" v-bind="data.tip">
      <div style="display:flex;align-items:center;width:0;word-break: keep-all;">
        <span v-if="data.tip.html" v-html="data.tip.html" > </span>
        <a-icon v-if="!data.tip.content || !data.tip.icon" type="question-circle" style="margin-left:10px;"/>
        <Container v-else :view="data.tip.content" :record="record" :column="column"/>
      </div>
    </a-tooltip>

    <!-- NOTE: 渲染组件  data中的key作为参数传递下去 -->
    <div :record="record" :column="column" v-bind="data" :is="data.component" ref="temp"
      @click="click" @change="change" @input.stop="input" @confirm="confirm">
      <!-- NOTE: 必须加该style，否则popconfirm下是children时不生效 -->
      <span style="display:inline">{{data.text}}</span>
      <Container v-if="data.children" :view="data.children" :record="record" :column="column" @reload="$emit('reload')"/>
    </div>

    <Container v-if="typeof data === 'string' || typeof data === 'number'" :record="record" :column="column" :view="data"/>
    <div v-else-if="data instanceof Array">
      <Container v-for="(item, index) in data" :key="index" :view="item" :record="record" :column="column" :body="body" :no_reload="no_reload"/>
    </div>

    <a-modal v-if="data.modal" :title="data.modal.title||data.text" :visible="data.modal.visible" :width="data.modal.width"
      @ok="handleOk" :confirmLoading="data.modal.confirmLoading" @cancel="data.modal.visible = false" >
      <Container ref="c_form" v-if="data.modal.content" :record="record" :column="column" :view="data.modal.content"/>
    </a-modal>
  </div>
</template>

<script>
import 'codemirror/mode/javascript/javascript';
import 'codemirror/theme/zenburn.css';
import Html from "./Html.vue";
import Layout from "./Layout.vue";
import Title from "./Title.vue";
import Echart from "./Echart.vue";
import Table from "./Table.vue";
import FilterView from "./FilterView.vue";

import Form from "./Form.vue";
import Button from "./Button.vue";
import Steps from "./Steps.vue";
import Modal from "./Modal.vue";
import Editor from "./common/Editor.vue";

export default {
  name: 'Container',
  components: {
    Html,
    Layout,
    Title,
    Echart,
    Table,
    Filters: FilterView,
    Form,
    Button,
    Steps,
    Modal,
    Editor,
  },
  props: ["view", "record", "column", "body", "no_reload"],
  watch: {
    view: async function (newV) {
      this.data = {};
      this.initData(newV);
      return newV;
    },
    // NOTE: record 为table 的每行参数，发生变化的时候，也要重新渲染
    record: async function (newV) {
      this.initData(this.view);
      return newV;
    },
  },
  data () {
    return {
      data: {},
      loading: false,
    };
  },
  mounted: async function () {
    this.initData(this.view);
  },
  methods: {
    initData: async function (view) {
      view = view || this.view;
      let date = new Date().getTime();
      this.loading = date;
      let data = await this.parseResult(view);
      if (this.loading !== date) {
        return ;
      }
      this.data = data;
      // if (this.data.component == "a-button") console.log(this.data);
      this.loading = false;
    },
    // NOTE: http 获取数据
    getAction: async function (id) {
      console.log(id,"22222");
      return this._http.get(`/api/v3/view/${id}`).then(async res => {
        let views = {};
        if (res.result) {
          views = await this.parseResult(res.result);
        }
        return views;
      });
    },
    // loading: function (loading) {
    //   let data = this.data;
    //   data.loading = loading;
    //   if (data.children && typeof data.children == 'object') {
    //     data.children = {...data.children, loading: loading};
    //   };
    //   this.data = {...data};
    // },
    parseResult: async function (data) {
      if (!data) return ;
      const loading = (loading, init) => {
        data.loading = loading;
        if (data.children && typeof data.children == 'object') {
          data.children = {...data.children, loading: loading};
        };
        data = {...data};
        if (!init) {
          this.data = {...data};
        }
      }
      if (data instanceof Array) {
        return data;
      } else if (typeof data == "object") {
        // NOTE: 初始化数据
        data = this.parseView(data);
        if (data.actionId) {
          console.log(data,"1111111");
          let result = await this._http.get(`/api/v3/view/${data.actionId}`);
          if (result.code) {
            this.$message.error(result.message);
          } else {
            data.content = result.result;
          }
        } else if (data.link) {
          data.click = () => this.$router.push(data.link);
        } else if (data.open) {
          data.click = () => window.open(data.open);
        } else if (data.postId) {
          loading(false, true);
          data.click = async () => {
            loading(true);
            let res = await this._http.post(`/api/v3/business/${data.postId}`,{record: this.record}).then(res => {
              if (!res.code) {
                this.$emit("reload");
              }
              loading(false);
            }, err => loading(false))
          };
          data.confirm = data.click;
        }
        return data;
      } else {
        return await this.getAction(data);
      }
    },
    parseView (data) {// NOTE: 初始化数据
      let {record} = this;
      const parseString = (obj = {}) => {
        // obj = {...obj}; // NOTE: debug  lambo
        Object.keys(obj || {}).forEach(key => {
          // NOTE: bind 和  init 不做${}解析处理
          if (key == 'init' || key == 'bind') {
            return ;
          }
          if (typeof obj[key] == 'string' && obj[key].indexOf("${")!=-1) {
            try {obj[key] = eval("`"+obj[key]+"`")} catch (e) { console.error(e)}
            // NOTE: 如果为其他类型，继续转义，支持基础数据类型
            try { obj[key] = eval(obj[key]); } catch (e) { }
          }
          if (obj[key] instanceof Array) {
            obj[key] = obj[key].map(e => typeof e == 'object' ? parseString(e) : e);
          } else if (typeof obj[key] == 'object') {
            obj[key] = parseString(obj[key]);
          }
        })
        return obj;
      }
      data = parseString(data);
      // NOTE: 初始化绑定数据
      if (data.bind) {
        Object.keys(data.bind).forEach(key => {
          try {
            data[key] = eval(`${data.bind[key]}`);
          } catch (e) {
            console.error(e);
          } finally {

          }
        })
        console.log(data);
        // data = data
      }
      // NOTE: 初始化执行js
      if (data.init) {
        let init = eval(data.init);
        if (typeof init == 'function') init = init(data, record, this) || {}
        data = { ...data, ...init }
      }
      if (data.modal) {
        data.modal = { ...data.modal, record: {}, visible: false, loading: false, confirmLoading: false }
      }
      return data;
    },
    click: async function (e) {
      let { data, record } = this;
      if (data.click) {
        if (!this._click) {
          this._click = typeof data.click == 'string' ? eval(data.click) : data.click;
        }
        this._click(e, data, this);
      // } else if (data.postId) {
      //   data.loading = true;
      //   let res = await this._http.post(`/api/v3/business/${button.postId}`,{record: this.record, ...(data.body || {})}).then(res => {
      //     if (!res.code) {
      //       if (data.no_reload) {// NOTE: 不刷新只提交reload事件，给表格刷新
      //         this.$emit('reload');
      //       } else {
      //         this.$e_emit('page_reload');
      //       }
      //     } else {
      //       this.$message.error((res.result && res.result.message) || res.message);
      //     }
      //     data.loading = false;
      //   }, err => {
      //     this.$message.error((res.result && res.result.message) || res.message);
      //     this.loading = false
      //   });
      } else {// NOTE: 弹出模态框的，一般不会有click事件，有也会click被覆盖
        if (data.modal) {
          // NOTE: 重置弹框内容
          data.modal.content = data.modal.content && {...data.modal.content};
          if (data.modal.actionId) {
            data.modal.loading = true;
            this._http.post(`/api/v3/business/${data.modal.actionId}`, {record}).then(res => {
              data.modal.loading = false;
              if (res.code) {
                data.modal.visible = false;
                this.$message.error(res.message);
              } else {
                data.modal.content = res.result;
              }
            });
          }
          data.modal.visible = true;
        }
      }
    },
    change (e) {
      let { data, record } = this;
      if (data.change) {
        if (!this._change) {
          this._change = typeof data.change == 'string' ? eval(data.change) : data.change;
        }
        this._change(e, data, this);
      }
    },
    input (e) {
      let { data, record } = this;
      if (data.input) {
        if (!this._input) {
          this._input = typeof data.input == 'string' ? eval(data.input) : data.input;
        }
        this._input(e, data, this);
      } else {
        window.eee = e;
        this.data.value = e.target.value
      }
    },
    confirm (e) {
      let { data, record } = this;
      if (data.confirm) {
        if (!this._confirm) {
          this._confirm = typeof data.confirm == 'string' ? eval(data.confirm) : data.confirm;
        }
        this._confirm(e, data, this);
      } else {
        this.click(e);
      }
    },
    handleOk: async function() {
      // if (!this.data.actionId) {
      //   throw "必须要参数actionId"
      // }
      let modal = this.data.modal;
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
          console.log(data, e);
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
      this.data.modal.confirmLoading = true;
      let res;
      if (modal.postId instanceof Function) {
        res = await modal.postId(data);
      } else {
        res = await this._http.post(`/api/v3/business/${modal.postId}`, {record: this.record, ...data});
      }
      this.data.modal.confirmLoading = false;
      if (res && res.code) {
        this.$message.error(res.message);
      } else {
        this.$emit("reload");
        this.$message.success(res.message);
        this.data.modal.visible = false;
      }
      return false;
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
