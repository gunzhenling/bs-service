<template>
  <div style="width:100%">
    <!-- <Container :view="{component: 'Form', id: 'ddd', content: [{type: 'editor',name:'富文本',value: `<p><img src='https://test-qiniu.intbee.com/FgsRkZVilAzM9bqVz0q7C390bpMu' style='max-width:100%;'><br></p>`}]}"/> -->
    <FilterView ref="filter" v-if="filters" :content="filters" :limit="filter && filter.limit" @change="e => filter = e"/>
    <div style="position:relative">
      <div style="position:relative">
        <div v-if="content && content.btns" class="table-btns">
          <Container :view="content.btns" @reload="reload"/>
          <!-- <a-button v-for="(item, index) in (content.btns instanceof Array ? content.btns : [content.btns])">
            {{item.content}}
          </a-button> -->
        </div>

        <div v-if="info" v-html="info"></div>
        <div v-else-if="pagination && pagination.total != undefined" style="text-align:left;margin:10px 0;"> 共 {{pagination.total}} 条 </div>
      </div>

      <a-table class="table-view" :class="{'no-padding':content.height}" v-bind="content" :columns="(columns.filter(e => !e.hidden))" :rowKey="(e,i) => content.rowKey && e[content.rowKey] || e['id'] || e['_id'] || i"
        :rowSelection="rowSelection"
        :dataSource="dataSource" :pagination="pagination" :loading="loading" @change="handleTableChange" :customRow="clickRow">
        <template v-for="(item,index) in (columns.filter(e => !e.hidden))" v-if="item.content || item.fixed || content.height || item._func" :slot="item.custom" slot-scope="text, record, index">
          <div v-if="!item.content" :style="`height:${content.height}px;display: flex;align-items: center;justify-content:center;overflow: hidden;`">{{item._func ? item._func(text, record) : text }}</div>
          <div v-else v-bind="item" :style="`height:${content.height}px;display: flex;align-items: center;justify-content:center;overflow: hidden;`">
            <TableItem :view="item.content" :record="record" :column="item" :index="index" @reload="reload"/>
          </div>
        </template>
      </a-table>

      <!-- NOTE: 全选功能 -->
      <div v-if="typeof selectAll == 'object'">
        <div class="selector" v-if="!selectAll.component && selectAll.options">
          <span v-if="selectAll.options">
            <a-select :disabled="loadingSelectAll" size="small" :value="selectAll.key" style="min-width:80px;"
              @change="changeSelectorKeys">
              <a-select-option v-for="(option,index) in selectAll.options" :key="index" :disabled="option.disabled" :value="option.value">{{option.name}}</a-select-option>
            </a-select>
          </span>
          <a-button @click="showSelectorModal" :loading="loadingSelectAll" size="small">查看</a-button>
          <a-button v-if="!selectAll.quick" :loading="loadingSelectAll" size="small" @click="clickSelectAll">{{selectAll.text || "确定"}}</a-button>
          <!-- <a-input :placeholder="selectAll.placeholder" v-model="selectAll._value" @pressEnter="e => changeValue(index, selectAll._value)"/> -->
        </div>
        <a-button :loading="loadingSelectAll" v-else-if="!selectAll.component && selectAll.text" type="primary" style="margin:20px;" v-bind="selectAll"
          @click="clickSelectAll">{{selectAll.text}}</a-button>
        <Container v-else-if="selectAll.component" :view="content.select"
          :record="{selectAll: selectedRowKeys, loading: loadingSelectAll}">{{content.select}}</Container>
      </div>

      <!-- NOTE: detail 点击每行detail显示弹窗或跳转 -->
      <a-modal v-if="record" v-bind="content.detail" :visible="Boolean(record)" @cancel="record = ''" @ok="modalConfirm">
        <div v-if="!record.modal">TODO</div>
        <Container v-else :view="record.modal" :record="record"></Container>
      </a-modal>
    </div>
  </div>
</template>
<script>
import Container from "./Container.vue";
import FilterView from "./FilterView.vue";
import TableItem from "./TableItem.vue";
import Title from "./Title.vue";
export default {
  components: {Container, FilterView, Title, TableItem},
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
  },
  props: ["content", "filters", "id", "query", "page", "limit"],
  watch: {
    content (newV) {
      // NOTE: 初始化详情弹窗确认按钮事件
      if (newV.detail && newV.detail.confirm && typeof newV.detail.confirm !== 'function') {
        newV.detail.confirm = eval(newV.detail.confirm);
      }
      // NOTE: 初始化详情弹窗 内容
      if (newV.detail && newV.detail.show && typeof newV.detail.show !== 'function') {
        newV.detail.show = eval(newV.detail.show);
      }
      this.init(newV);
      return newV;
    },
    filter (newV) {
      this.getAction(newV, this.content)
      return {...newV};
    },
    rowSelection (newV) {
      return newV;
    },
    columns (newV) {
      if (newV) {
        newV.forEach(e => {
          e.scopedSlots = (e.content || e.fixed || this.content.height || e._func) && { customRender: e.dataIndex };
          // NOTE: Title 字段会去设置 antd 自带的 header
          e.custom = e.dataIndex == 'title' ? `_${e.dataIndex}` : e.dataIndex;
        })
      }
      return newV;
    },
    dataSource (newV) {
      if (!newV) {
        return newV;
      }
      // NOTE: 有可能是设置了 selectedRowKeys
      if (this.selectedRowKeys.length == this.selectedRows) {
        return newV;
      } else {
        let selectedRows = this.selectedRowKeys.map(e => {
          let index = newV.findIndex(item => item.id == e);
          return newV[index];
        }).filter(e => e);
        this.$emit("onSelectChange", {selectedRowKeys: this.selectedRowKeys, selectedRows});
      }
      newV.forEach(e => {
        // NOTE: Title 字段会去设置 antd 自带的 header
        if (e.title != undefined) e._title = e.title;
      })
      return newV;
    },
    pagination (newV) {
      newV.hideOnSinglePage = newV.hideOnSinglePage == undefined ? true : newV.hideOnSinglePage;
      return newV;
    },
    selectedRowKeys (newV) {
      this.rowSelection = {
        selectedRowKeys: newV || [],
        onChange: this.onSelectChange,
        onSelect: this.onSelect,
        getCheckboxProps: record => {
          return {
            disabled: this.selectAll.disabled && eval(this.selectAll.disabled),
            name: record.name,
          };
        }
      }
      return newV || [];
    },
  },
  data() {
    return {
      data: [],
      pagination: {hideOnSinglePage: true},
      loading: false,
      columns: [],
      dataSource: [],
      info: "",
      filter: {limit: 10},
      rowSelection: undefined,
      selectAll: undefined,
      selectedRowKeys: [],
      loadingSelectAll: false,
      showDetail: false,
      record: "",
    }
  },
  mounted () {
    if (this.query) {
      this.$e_on(this.query, (data) => {
        this.filter = data
      });
    }
    if (this.content) {
      this.init(this.content);
    }
  },
  methods: {
    init (content) {
      if (!content) {return ;}
      content.bordered = content.bordered == undefined ? true : content.bordered;
      // NOTE: 会触发获取watch filter 进而去加载
      if (this.id) {
        this.$e_emit(this.id, {offset: (content.page || 1) * ( content.limit || 10),limit: content.limit || 10});
      } else if (!this.query) {
        // this.filter.page = content.page || 1;
        // this.filter.limit = content.limit || 10;
        // NOTE: 有filter获取filter的初始化数据
        if (this.$refs.filter) {
          this.$refs.filter.changeValue();
        } else {
          this.getAction(this.filter, this.content);
        }
      }
      // NOTE: 添加监听
      this.initEvents(content);
      if (content.evalFunc) {
        eval(content.evalFunc);
      }
      this.columns = content.columns || this.columns;
      this.selectedRowKeys = content.selectedRowKeys || this.selectedRowKeys;
      if (content.selectAll) {
        this.selectAll = content.selectAll;
        console.log(this.selectAll);
        if (this.selectAll.options) {
          this.selectAll.key = this.selectAll.options[0].value
        }
        this.rowSelection = {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange,
          onSelect: this.onSelect,
          getCheckboxProps: record => {
            return {
              disabled: this.selectAll.disabled && eval(this.selectAll.disabled),
              name: record.name,
            };
          }
        }
      }
      if (!content.actionId) {
        if (typeof content.dataSource !== "string") {
          this.dataSource = content.dataSource || this.dataSource;
        }
        if (content && content.pagination != undefined) {
          this.pagination = content.pagination;
        }
      }
    },
    getAction: async function (filter, content) {
      content = content || this.content;
      if (!content.actionId) {
        return this.$emit("change", filter);
      }
      this.loading = true;
      if (filter) {
        filter.offset = ((filter.page || 1) - 1) * ( filter.limit || 10);
      }
      let res = await this._http[content.actionMethod||'get'](`/api/${content.actionId}`, {...filter||{},page: undefined});
      if (res.result) {
        this.info = res.result.info;
        this.dataSource = res.result.data;
        if (res.result.total != undefined) {
          this.pagination = {
            total: +res.result.total,
            current: +res.result.page,
            pageSize: res.result.limit || (this.pagination && this.pagination.pageSize) || 10,
          }
        }
      } else {
        this.$message.error(res.message);
      }
      this.loading = false;

    },
    handleTableChange (pagination, filter, sorter) {
      // sort = { [`${sort.field}`]: { order: sort.order == -1 ? "desc" : "asc"} };
      if (sorter && sorter.field) {
        this.filter.sort = {
          field: sorter.field,
          order: sorter.order.indexOf("asc")
        };
      } else {
        this.filter.sort = undefined;
      }
      if (this.id) {
        this.$e_emit(this.id, {...this.filter, page: pagination.current});
      } else {
        this.filter.page = pagination.current;
        // this.filter.limit = this.filter.limit || pagination.size || 10;
        this.getAction(this.filter, this.content);
      }
    },
    changeSelectorKeys (value) {
      this.selectAll.key = value;
      this.selectAll = {...this.selectAll}
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.$emit("onSelectChange", {selectedRowKeys, selectedRows});
      let {dataSource} = this.dataSource;
      this.selectedRowKeys = selectedRowKeys;
      this.rowSelection = {
        ...this.rowSelection,
        selectedRowKeys: this.selectedRowKeys,
      }
    },
    onSelect (record, selected, selectedRows) {
      // NOTE: 父选项勾选/取消子选项自动勾选/取消
      if (record.children) {
        record.children.map(e => {
          if (selected && !selectedRows.some(_e => _e.id == e.id)) {
            selectedRows.push(e);
          } else if (!selected && selectedRows.some(_e => _e.id == e.id)) {
            let index = selectedRows.findIndex(_e => _e.id == e.id);
            selectedRows.splice(index,1);
          }
        })
        this.onSelectChange(selectedRows.map(e => e.id), selectedRows);
      }
    },
    clickSelectAll: async function () {
      let selectAll = this.content.selectAll;
      if (!this.selectedRowKeys || !this.selectedRowKeys.length) {
        return this.$message.error("请选择操作");
      }
      this.loadingSelectAll = true;
      let result = await this._http.post(`/api/${selectAll.postId}`, {[this.selectAll.key || `_select`]: this.selectedRowKeys, });
      if (result.code) {
        this.$message.error(result.message);
      } else {
        this.reload();
        this.$message.success(result.message);
      }
      this.loadingSelectAll = false;
      selectAll.callback && selectAll.callback(result);
    },
    showSelectorModal () {
      this.record = {
        modal: this.content
      }
    },
    reload () {
      this.handleTableChange({current: 1});
    },
    clickRow (record, index) {
      // if (this.content.detail)
      return {
          on: {
             click: async () => {
               let detail = this.content.detail;
               if (detail) {
                 let modal = {};
                 if (detail.show) {
                   this.loading = true;
                   modal = await detail.show(record, detail);
                   this.loading = false;
                 }
                 this.record = {...record, modal, visible: false}
               }
             }
          }
      }
    },
    modalConfirm: async function () {
      let record = this.record;
      let detail = this.content.detail;
      let form = {};
      ((record.modal instanceof Array) ? record.modal : [record.modal]).forEach(e => {
        if (e.component == "Form") {
          e.content.forEach(e => {
            form[e.key] = e.value;
            if (e.type == 'table' && !e.no_must && (!e.value || !e.value.length)) {
              this.$message.error("必须选中一个数据");
              // NOTE: 打断后面操作
              throw ''
            }
          })
        }
      });
      if (detail.confirm) {
        await detail.confirm({form, record: this.record}, (res) => {
            if (res.code) {
              this.$message.error(res.message);
            } else {
              this.$message.success(res.message);
              this.record = "";
            }
        });
      } else {
        this.record = '';
      }
    }
  },
}
</script>

<style lang="less">
.table-btns {
  position: absolute;
  right: 0;
  bottom: 0;
  // bottom: calc(100% + 10px);
  display: flex;
  >* {
    margin-left: 10px;
  }
}
.selector-btn {
  margin-top: 20px;
}
.table-view {
  max-width: 100%;
  table {
    max-width: 100%;
  }
  thead th, thead td {
    word-break: keep-all;
  }
  td,th {
    word-break: break-all;
    text-align: center!important;
  }
  &.no-padding {
      td {
        padding: 0;
      }
  }
}

.selector {
  margin-top: -45px;
  display: flex;
  align-items: center;
  word-break: keep-all;
  >* {
    margin-right: 10px;
  }
}
</style>
