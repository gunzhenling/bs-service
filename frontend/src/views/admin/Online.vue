<template>
  <div class="">
    <Title content="线上功能"/>
    <Table :content="{columns, bordered: false, filters, dataSource, pagination, btns}" @change="changeFilters"/>
    <a-modal title="查看" width="900px" :visible="visible" :confirmLoading="loading" @cancel="visible = false" :footer="null">
      <div class='codemirror'>
        <textarea ref="mycode" style="width:100%;max-height:600px;" :value="code"> </textarea>
      </div>
    </a-modal>
  </div>
</template>

<script>
import * as CodeMirror from 'codemirror/lib/codemirror'
import 'codemirror/mode/javascript/javascript';
import 'codemirror/theme/yonce.css';
import 'codemirror/lib/codemirror.css';
import Title from "../../components/Title.vue";
import Table from "../../components/Table.vue";
export default {
  props: ["view"],
  components: {Title, Table},
  data () {
    // 函数编号	功能函数名	父级标签	访问级别	函数类型	函数状态	操作
    return {
      visible: false,
      loading: false,
      code: "",
      columns: [
        {title: "函数编号",dataIndex:"id"},{title: "功能函数名",dataIndex:"title"},{title: "父级标签",dataIndex:"tab"},
        {title: "访问级别",dataIndex:"_action_type"},{title: "函数类型",dataIndex:"_type"},
        {title: "函数状态",dataIndex:"_status"},{
          title: "操作",dataIndex:"operation", content: {
              component: "Layout",
              init: `(data, record) => record.id == 'other' &&  {component: ''}`,
              content: {
                children: [
                  {
                    component: "a-button", type: "primary", style: "margin-right: 10px;", text: "查看",
                    click: (e, column,that) => this.setCode(that.record)
                  },
                  // {component: "a-button", text: "下线功能"}
                ]
              }
            }
        }],
      groups: [],
      filters: [],
      dataSource: [],
      pagination: {},
      btns: [{
        component: "a-button", type: "primary", text: "初始化", loading: false,
        click: async (e,data) => {
          data.loading = true;
          await this.initFunc();
          data.loading = false;
        }
      }],
    };
  },
  mounted () {
    this.getAdmins();
  },
  methods: {
    getAdmins () {
      this._http.get(`/api/v3/business/actions`).then(res => {
        console.log(res);
        this.groups = res.result.map((group, groupIndex) => {
          group.items.sort((item1, item2) => {
            return item2.sort - item1.sort;
          });
          group.items.forEach(e => {
            this.setChlidren(group.items, e);
            e._action_type = {"N": "权限访问"}[e.action_type];
            e._type = {"view": "页面函数"}[e.type] || "功能函数";
            e._status = e.status === undefined ? "-" : e.status === 0?"正常":"已下线"
          });
          group.items = group.items.filter(e => e.type == "view");
          group.items.push({id: "other", title: "其他", _type: "-", _action_type: "-", _status: "-"});
          if (groupIndex == 0) {
            this.dataSource = group.items;
          }
          return group;
        });
        this.filters = [
          {
            name: "选择功能组：",
            type: "selector",
            key: "group",
            options: this.groups.map((group, index) => ({name: group.title, value: index}))
          }
        ];
        this.pagination = {
          pageSize: res.result[0].items.length,
        };
      })
    },
    setChlidren (dataSource, obj) {
      let index = dataSource.findIndex(e => {
        return obj.title.indexOf(`[${e.title}]`) == 0;
      });
      if (dataSource[index]) {
        dataSource[index].children = dataSource[index].children || [];
        dataSource[index].children.push(obj);
      }
    },
    setCode (record) {
      console.log(record);
      this.loading = true;
      this._http.get('/api/v3/business/comp?action=' + record.id).then(res => {
        this.loading = false;
        this.visible = true;
        if (!res.code) {
          this.code = res.result.o;
          if (!this.codeMirror) {
            this.$nextTick(() => {
              this.codeMirror = CodeMirror.fromTextArea(this.$refs.mycode, {readOnly:true, mode: "javascript", theme:"yonce" });
            })
          } else {
            this.$nextTick(() => {
              this.codeMirror.doc.setValue(res.result.o);
            })
          }
        }
      })
    },
    initFunc: async function () {
      let res = await this._http.post('/api/v3/business/init')
      if (res.code) {
        this.$message.error(res.message);
      } else {
        this.$message.success("初始化成功");
      }
    },
    changeFilters (e) {
      // DEBUG:
      // this.dataSource = this.groups[e.group] && this.groups[e.group].items || [];
    }
  }
}
</script>

<style lang="less">
.codemirror {
  overflow:auto;word-break:break-all;max-height:600px;width:100%;position:relative;
  .CodeMirror{
    height: 100%;
  }
}
</style>
