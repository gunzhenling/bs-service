<template>
  <div class="form">
    <div v-for="(item,index) in data" :key="index" :style="item.float?'float: left;':'clear: both;'">
      <div class="form-item" v-if="item.type === 'editor'">
        <span>{{item.name}}</span>
        <div class="" :style="item.style">
          <Editor :value="item.value" @change="e => changeValue(index, e)"/>
        </div>
      </div>
      <div class="form-item" v-if="item.type === 'selector'">
        <span>{{item.name}}</span>
        <a-select :value="item.value" :disabled="item.disabled || (item.options || []).length==0" style="min-width: 80px" @change="e => changeValue(index, e == 'all' ? '' : e)">
          <a-select-option v-for="(option,index) in item.options" :key="index" :disabled="option.disabled" :value="option.value">{{option.name}}</a-select-option>
        </a-select>
      </div>
      <div class="form-item" v-if="item.type === 'input' || item.type == undefined">
        <span>{{item.name}}</span>
        <a-input v-bind="item" @change="e => changeValue(index, e.target.value)"/>
      </div>
      <div class="form-item" v-if="item.type === 'inputs'">
        <span>
          {{item.name}}
          </br>
          <a-switch  v-if="item.Switch" :checked="item.Switch.checked"  @change="e => changeSwith(index, e)"/>
          <a-button  v-else="!item.keys" size="small" @click="e => changeInputs(index, item.value.length, '')">增加</a-button>
        </span>
        <template v-if="!item.options">
          <div v-for="(e,_index) in item.value" :key="_index">
            <a-input style="width:78px;margin-right:5px" v-bind="item" :value="e" @change="e => changeInputs(index, _index, e.target.value)"/>
            <a-button size="small" style="margin-right:10px" @click="e => changeInputs(index, _index)">删除</a-button>
          </div>
        </template>
        <template v-else>
          <div v-for="(e,_index) in item.value" :key="_index" style="display:flex;align-items:center">
            <div class="">
              <div v-if="item.Switch || item.keys" style="text-align:center;">{{e.name}}</div>
              <div v-for="(option,o_index) in item.options" :key="o_index">
                <span style="display:inline-block;width:60px;" v-if="option.name">{{option.name}}</span>
                <a-input style="width:78px;margin-right:5px" v-bind="item" :value="e[option.value]" @change="e => changeInputs(index, _index, e.target.value, option.value)"/>
              </div>
            </div>
            <a-button v-if="!item.keys && !item.Switch" size="small" style="margin-right:10px;text-align:center;" @click="e => changeInputs(index, _index)">删除</a-button>
          </div>
        </template>
      </div>
      <div class="form-item" v-if="item.type === 'number'">
        <span>{{item.name}}</span>
        <a-input-number v-bind="item" @change="e => changeValue(index, e)"/>
      </div>
      <div class="form-item" v-if="item.type === 'textarea'">
        <span>{{item.name}}</span>
        <a-textarea v-bind="item" @change="e => changeValue(index, e.target.value)"/>
      </div>
      <div class="form-item" v-if="item.type == 'link-select'">
        <span>{{item.name}}</span>
        <LinkSelector :dataSource="item.dataSource" :showAll="item.showAll == undefined ? true : item.showAll"/>
      </div>

      <div class="form-item" v-if="item.type == 'date-date'">
        <span>{{item.name}}</span>
        <a-date-picker @change="e => changeValue(index, e)" />
      </div>
      <div class="form-item" v-if="item.type == 'date-month'">
        <span>{{item.name}}</span>
        <a-month-picker @change="e => changeValue(index, e)" />
      </div>
      <div class="form-item" v-if="item.type == 'date-range'">
        <span>{{item.name}}</span>
        <a-range-picker @change="e => changeValue(index, e)" />
      </div>
      <div class="form-item" v-if="item.type == 'switch'">
        <span>{{item.name}}</span>
        <a-switch v-bind="item.attr" :checked="item.value" @change="e => changeValue(index, e)" />
      </div>
      <div class="form-item" v-if="item.type == 'table'">
        <Table :content="item.content" @onSelectChange="({selectedRowKeys, selectedRows}) => changeValue(index, selectedRowKeys)"/>
      </div>
      <div class="form-item" v-if="item.type == 'image'">
        <span>{{item.name}}</span>
        <a-upload name="picture_file" :action="'/api/bs/file/upload/image'"
          listType="picture-card" :fileList="item.value" @preview="handlePreview" @change="e=>changeImages(e,index)" >
          <div v-if="item.value.length < (item.limit || 1)">
            <a-icon type="plus" />
            <div class="ant-upload-text">上传</div>
          </div>
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-upload>
      </div>
    </div>
    <Container v-if="btn" :view="{component: 'Button', content: {...btn, body: values}}"/>
  </div>
</template>

<script>
import LinkSelector from "./common/LinkSelector";
import Table from "./Table";
import Editor from "./common/Editor";
export default {
  components: {LinkSelector, Table, Editor},
  props: ["content", "btn", "id", "record"],
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
  },
  watch: {
    content (newV) {
      this.init(newV);
      return newV;
    },
    record (newV) {
      this.init(this.content, newV);
      return newV;
    },
    data (newV) {
      this.values = this.arrToObj(newV)
      return newV;
    }
  },
  data () {
    return {
      data: [],
      qiniuConfig: "",
      loadingImage: false,
      previewImage: '',
      previewVisible: false,
      values: {},
    }
  },
  mounted () {
    this.init(this.content);
  },
  methods: {
    myElse () {
      for (let i = 0; i < arguments.length; i++) {
        if (arguments[i] != undefined) {
          return arguments[i];
        }
      }
    },
    init: async function (content, record) {
      for (var i = 0; i < content.length; i++) {
        // if (content[i].type == "image" && !this.qiniuConfig) {
        //   await this._http.get('/api/v3/upload-token?key=undefined').then(res => {
        //     res.result.host = res.result.host.filter(e => e.indexOf(location.protocol) != -1)[0];
        //     this.qiniuConfig = res.result;
        //   })
        // }
      }
      this.data = content.map(e => {
        e.value = record ? record[e.key] : this.myElse((this.record || {})[e.key], e.value, e.defaultValue);
        if (e.type == "selector") {
          e.value = this.myElse(e.value, e.options && e.options[0] && e.options[0].value);
          if (e.$eemit) {
            setTimeout(() => {
              this.$e_emit(e.$eemit, e.options && e.options[0]);
            },10)
          }
        } else if (e.type == "image") {
          e.value = e.value ? (e.value instanceof Array ? e.value : [e.value]) : [];
          e.value = e.value.map((item,index) => ({uid: index, name: index + "", url: item}))
        } else if (e.type == "inputs") {
          if (e.Switch) {
            console.log(e);
            e.Switch.checked = false;
            if (e.value) {
              e.value.forEach((item, i) => {
                Object.assign(item, e.Switch[i])
              });
              if (e.value.length == 2) e.Switch.checked = true;
            } else {
              e.value = [e.Switch[0]]
            }
          } else {
            e.value = e.value || (e.keys ? e.keys : [e.options?{}:'']);
          }
        }
        return e;
      });
        console.log(  this.data);
      this.changeValue(0, this.data[0].value);
    },
    changeSwith (index,e) {
      this.data[index].Switch.checked = e;
      if (e) {
        this.data[index].value.push(this.data[index].Switch[1]);
      } else {
        this.data[index].value = [this.data[index].value[0]];
      }
      this.$forceUpdate();
    },
    changeInputs (index,_index,e, key) {
      if (e == undefined) {
        this.data[index].value.splice(_index, 1);
      } else if (this.data[index].value[_index] == undefined) {
        this.data[index].value.push(this.data[index].options?{}:'')
      } else {
        if (key) {
          this.data[index].value[_index][key] = e;
        } else {
          this.data[index].value[_index] = e;
        }
      }
      this.$forceUpdate();
      if (this.data[index].$eemit) {
        this.$e_emit(this.data[index].$eemit, this.data[index].value);
      }
      if (this.id) {
        window[this.id] = this.data;
      }
      this.$emit("change", this.data);
    },
    changeValue (index, e) {
      this.data[index].value = e;
      this.data = [].concat(this.data);
      if (this.data[index].type == "selector") {
        let oindex = this.data[index].options.findIndex(item => item.value == e);
        if (this.data[index].$eemit) {
          this.$e_emit(this.data[index].$eemit, this.data[index].options[oindex]);
        }
      } else {
        if (this.data[index].$eemit) {
          this.$e_emit(this.data[index].$eemit, this.data[index].value);
        }
      }
      if (this.id) {
        window[this.id] = this.data;
      }
      this.$emit("change", this.data);
      // let obj = {}
      // this.data.forEach(e => {
      //   if (!e.key) return;
      //   if (e.type == "link-select" && e.value) {
      //     obj[e.key] = e.value;
      //   } else if (e.type == "selector") {
      //     obj[e.key] = e.value != "all" ? e.value : "";
      //   } else if (e.value != undefined) {
      //     obj[e.key] = e.value;
      //   }
      // });
    },
    changeImages (e,index) {
      let {file,fileList} = e;
      this.data[index].value = fileList;
      this.data = [].concat(this.data);
    },
    handlePreview(file) {
      // this.previewImage = file.url || file.thumbUrl;
      // this.previewVisible = true;
    },
    handleCancel() {
      this.previewVisible = false;
    },
    beforeUpload(e, index) {
      this.data[index].value = e;
      this.data = [].concat(this.data);
      return false;
    }
  }
}
</script>

<style scoped lang="less">
.form {
  .form-item {
    display: flex;
    align-items: center;
    padding-right: 15px;
    padding-bottom: 10px;
    word-break: keep-all;
    >span:first-child {
      min-width: 100px;
      text-align: right;
      padding-right: 10px;
      font-weight: bold;
    }
  }
}
</style>
