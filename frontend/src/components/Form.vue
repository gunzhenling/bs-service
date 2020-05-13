<template>
  <div class="form">
    <div v-for="(item,index) in data" :key="index">
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
        <a-upload v-if="qiniuConfig.host" :action="qiniuConfig.host" :data="{token: qiniuConfig.token}"
          listType="picture-card" :fileList="item.value" @preview="handlePreview" @change="e=>changeImages(e,index)" >
          <div v-if="item.value.length < (item.limit || 1)">
            <a-icon type="plus" />
            <div class="ant-upload-text">Upload</div>
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
      this.init(this.content);
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
    init: async function (content) {
      for (var i = 0; i < content.length; i++) {
        if (content[i].type == "image" && !this.qiniuConfig) {
          await this._http.get('/api/v3/upload-token?key=undefined').then(res => {
            res.result.host = res.result.host.filter(e => e.indexOf(location.protocol) != -1)[0];
            this.qiniuConfig = res.result;
          })
        }
      }
      this.data = content.map(e => {
        e.value = this.myElse((this.record || {})[e.key], e.value, e.defaultValue);
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
        }
        return e;
      });
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
      this.previewImage = file.url || file.thumbUrl;
      this.previewVisible = true;
    },
    handleCancel() {
      this.previewVisible = false;
    },
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
