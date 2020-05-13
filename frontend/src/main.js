import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import resource from 'vue-resource'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import http from './lib/http';
import utils from './lib/utils';
import moment from 'moment';

Vue.config.productionTip = false;
Vue.prototype.$formatDate = utils.formatDate;
Vue.prototype.GLOBAL = {};

let _events_ = {
  list: [],
};
let _emitObj_ = {}// NOTE:
Vue.mixin({
  data () {
    return {}
  },
  beforeDestroy () {
    Object.keys(this._events_ || {}).forEach(e => {
      this.$e_rm(e, this._events_[e]);
    })
    Object.keys(this._emitObj_ || {}).forEach(e => {
      delete _emitObj_[e];
    })
  },
  methods: {
    arrToObj (arr, key="key", value="value") {
      let obj = {};
      (arr || []).forEach(e => {
        obj[e[key]] = e[value];
      })
      return obj;
    },
    moment,
    bindFunc (content) {
      let {record, column} = this;
      const parse = (data) => {
        data = {...data};
        if (data && data.bind) {
          Object.keys(data.bind).forEach(key => {
            try {
              data[key] = eval(`${data.bind[key]}`);
            } catch (e) {
              console.log(data.bind[key]);
              console.error(e);
            } finally {
            }
          })
        }
        return data;
      }
      if (content instanceof Array) {
        content = content.map(parse);
      } else {
        content = parse(content);
      }
      return content;
    },
    initEvents (content) {
      // NOTE: 添加监听
      Object.keys(content).forEach(key => {
        if (key == "e_on") {
          Object.keys(content[key]).map(real_key => {
            this[real_key] = undefined;
            let eon = content[key][real_key].match(/(?<=\$eon\[)[^\]]+/);
            if (!eon) {
              return ;
            } else {
              eon = eon[0];
            }
            this.$e_on(eon, (data) => {
              console.log(this[real_key]);
              this[real_key] = eval(`${content[key][real_key].replace(`$eon[${eon}]`, "data")}`);
              if (key == "dataSource") {
                this.pagination = {
                  // total: this[real_key].length || 0,
                  current: 1,
                  pageSize: (this[key] || []).length,
                }
              }
            })
          });
          return ;
        }
        if (typeof content[key] !== 'string') {
          return ;
        }
        this[key] = undefined;
        let eon = content[key].match(/(?<=\$eon\[)[^\]]+/);
        if (!eon) {
          return ;
        } else {
          eon = eon[0];
        }
        this.$e_on(eon, (data) => {
          this[key] = eval(`${content[key].replace(`$eon[${eon}]`, "data")}`);
          if (key == "dataSource") {
            this.pagination = {
              // total: this[key].length || 0,
              current: 1,
              pageSize: (this[key] || []).length,
            }
          }
        })
      })
    },
    $e_on (id, cb) {
      this._events_ = this._events_ || {};
      // if (this._events_[id]) {
      //   return ;
      // }
      this._events_[id] = cb;
      // NOTE: 初始化时查看有emit过的，先直接调用一次
      if (_emitObj_[id] != undefined) {
        cb(_emitObj_[id]);
      }
      console.log("on", id );
      _events_.list.push({ id, cb });
    },
    $e_emit (id, data) {
      console.log("emit", id,data, );
      console.log(_events_.list);

      this._emitObj_ = this._emitObj_ || {};
      this._emitObj_[id] = data;
      _emitObj_[id] = data;
      _events_.list.forEach(e => {
        if (e.id == id) {
          e.cb(data);
        }
      })
    },
    $e_rm (id, cb) {
      console.log(id);
      _events_.list.forEach((e, index) => {
        if (e.id == id && e.cb == cb) {
          _events_.list.splice(index, 1)
        }
      })
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
      if (modal.postId instanceof Function) {
        res = await modal.postId(data);
      } else {
        res = await this._http.post(`/api/v3/business/${modal.postId}`, {record: this.record, ...(modal.body || {}), ...data});
      }
      return res;
    }
  }
});

Vue.use(Antd)
Vue.use(resource);
Vue.prototype._http = http(Vue);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
