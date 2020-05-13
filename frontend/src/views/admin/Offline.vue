<template>
  <div class="">
    <Title content="功能审核"/>
    <Container v-if="view" :view="view"/>
    <!-- <Table :content="{columns, dataSource, pagination, btns, selectAll}" :filters="filters" @change="changeFilters"/> -->
    <a-modal title="查看更新" width="1200px" :visible="visible" :confirmLoading="loading" @cancel="visible = false" :footer="null">
      <div class="codemirror">
        <div class="">
          <textarea ref="o_mycode" style="height: auto;"> </textarea>
        </div>
        <div class="">
          <textarea ref="n_mycode" style="height: auto;"> </textarea>
        </div>
      </div>
      <div class="codemirror">
        <a class="item">
          旧代码
        </a>
        <a class="item">
          新代码
        </a>
      </div>
    </a-modal>
  </div>
</template>

<script>
import CodeMirror from 'codemirror';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/theme/yonce.css';
import 'codemirror/lib/codemirror.css';
import Title from "../../components/Title.vue";
import Table from "../../components/Table.vue";
import Container from "../../components/Container.vue";
export default {
  components: {Title, Container},
  data () {
    // 函数编号	功能函数名	父级标签	访问级别	函数类型	函数状态	操作
    return {
      view: '',
      visible: false,
      loading: false,
      code: "",
      // columns: ,
      groups: [],
    };
  },
  mounted () {
    this.getWaitOnline();
  },
  methods: {
    getWaitOnline () {
      this._http.get(`/api/v3/business/wait-online`).then(res => {
        let groups = {};
        res.result.forEach(item => {
          if (groups[item.group_name]) {
            groups[item.group_name].push(item);
          } else {
            groups[item.group_name] = [item];
          }
        });
        groups = Object.keys(groups).map(group_name => ({group_name, items: groups[group_name]}));

        this.groups = groups.map((group, groupIndex) => {
          group.id = groupIndex+'';
          if (!group.items) {
            return ;
          }
          group.items.sort((item1, item2) => {
            return item2.sort - item1.sort;
          });
          group.items.forEach(e => {
            e._action_type = {"N": "权限访问"}[e.action_type];
            e._type = {"view": "页面函数"}[e.type] || "功能函数";
            e._status = e.status === undefined ? "-" : e.status === 0?"正常":"已下线"
          });
          if (groupIndex == 0) {
            this.dataSource = group.items;
          }
          return group;
        });
        if (this.groups.length > 0) {
          this.filters = [
            {
              name: "选择功能组：",
              type: "selector",
              key: "group",
              options: this.groups.map((group, index) => ({name: group.group_name, value: index+''}))
            }
          ];
        } else {
          this.filters = [
            {
              name: "选择功能组：",
              type: "selector",
              key: "group",
              disabled: true,
              options: [{name: "没有可选", value: 0}]
            }
          ];
          this.dataSource = [];
        }
        this.pagination = {
          pageSize: this.dataSource.length,
        };

        console.log(this.groups);
        this.view = [
          {
            component: "Form",
            // on: {
            //   `btn.disabled`: `($eon[action_group] || {items:[]}).items.length`,
            // },
            btn: {
              postId: `online`,
              text: "上线",
              type: "danger",
              cb: () => {
                this.getWaitOnline();
              }
            },
            content: [
              {"$eemit": "action_group", type: "selector", name: "选择功能组", key: "action_group",
                value: (this.groups[0] || {}).id, options: this.groups.map(e => ({name: e.group_name, value: e.id, items: e.items}))},
              {
                type: "table",
                key: "actions",
                content: {
                  selectAll: {
                    key: "actions",
                  },
                  dataSource: `($eon[action_group] || {}).items`,
                  columns: [{title: "函数编号",dataIndex:"id"},{title: "功能函数名",dataIndex:"title"},{title: "父级标签",dataIndex:"tab"},
                    {title: "访问级别",dataIndex:"_action_type"},{title: "函数类型",dataIndex:"_type"},
                    {title: "函数状态",dataIndex:"_status"},{
                      title: "操作",dataIndex:"operation", content: {
                          component: "Button",
                          content: [
                            {
                              text: "查看更新",
                              type: "primary",
                              click: this.setCode,
                            }, {
                              text: "删除",
                              title: "确定删除该函数？",
                              type: "danger",
                              click: this.deleteData,
                            }
                          ]
                        }
                      }
                    ]
                }
              }
            ]
          }
        ]

      })
    },
    setCode (record) {
      this.loading = true;
      this._http.get('/api/v3/business/comp?action=' + record.id).then(res => {
        this.loading = false;
        this.visible = true;
        if (!res.code) {
          this.code = res.result.o;
          if (!this.codeMirror) {
            this.$nextTick(() => {
              this.o_codeMirror = CodeMirror.fromTextArea(this.$refs.o_mycode, { mode: "javascript", theme:"yonce" });
              this.n_codeMirror = CodeMirror.fromTextArea(this.$refs.n_mycode, { mode: "javascript", theme:"yonce" });
                this.o_codeMirror.doc.setValue(res.result.o);
                this.n_codeMirror.doc.setValue(res.result.n);
            })
          } else {
            this.$nextTick(() => {
              this.o_codeMirror.doc.setValue(res.result.o);
              this.n_codeMirror.doc.setValue(res.result.n);
            })
          }
        }
      })
    },
    deleteData: async function (record) {
      let index = this.dataSource.findIndex(e => e === record);
      this.dataSource[index] = {...record, loading: true};
      this.dataSource = [].concat(this.dataSource)

      let result = await this._http.delete(`/api/v3/business/waiting`, {action: record.id});

      this.dataSource[index] = {...record, loading: false};
      this.dataSource = [].concat(this.dataSource)

      if (result.code) {
        this.$message.error(result.message);
      } else {
        this.$message.success(result.message);
      }
      this.getWaitOnline();
    },
    upload (e) {
      if (e.group) {
        this.dataSource = this.groups[e.group].items;
      }
    }
  }
}
</script>

<style lang="less" scoped>
.codemirror {
  overflow-y:auto;
  overflow-x:hidden;
  word-break:break-all;
  max-height:600px;
  width:100%;
  position:relative;
  display: flex;
  align-items: flex-start;
  >* {
    flex: 1;
    width: 50%;
    height: auto;
    overflow: hidden;
    &:first-child {
      margin-right: 20px;
    }
  }
  .item {
    margin: 10px;
    margin-bottom: 0;
    text-align: center;
    font-weight: bold;
    font-size: 20px;
  }
}
</style>
