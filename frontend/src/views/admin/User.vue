<template>
  <div class="">
    <Title content="管理员"/>
    <Table :content="{columns, dataSource, pagination, btns, detail}"/>
  </div>
</template>

<script>
import Title from "../../components/Title.vue";
import Table from "../../components/Table.vue";
export default {
  props: ["view"],
  components: {Title, Table},
  data () {
    // 名称	手机号	角色	邮箱	创建时间	状态	操作
    return {
      columns: [
        {title: "名称",dataIndex:"name"},{title: "手机号",dataIndex:"mobile"},{title: "角色",dataIndex:"_role"},
        {title: "邮箱",dataIndex:"email"},{title: "创建时间",dataIndex:"create_time"},
        {title: "操作",dataIndex:"edit", content: [
          {text: '查看详情', type: 'primary', modal: {
            content: {}
          }}
        ]},
        {title: "状态",dataIndex:"_status"},{title: "操作",dataIndex:"operation", content: [
          {component: "a-button", text:"",loading: false, getAdmins: this.getAdmins,
            init: `(data,record) => ({text: record.status ? "恢复" : "禁用"})`,
             click: `() => {
               this.data.loading = true;
               this._http.put("/api/v3/admin-status", {admin_id: this.record.id,status: !this.record.status ? 1: 0}).then(res => {
                 this.data.loading = false;
                 if (res.code) {
                   this.$message.error(res.message);
                 } else {
                   this.data.getAdmins();
                   this.$message.success("操作成功");
                 }
               });
             }`}
          ]
        }],
      dataSource: [],
      pagination: {},
      btns: {component: "a-button", text: "创建管理员",
        modal: {
          actionId: (data) => {
            // NOTE: http 返回 500，不处理会被throw掉
            return this._http.post('/api/v3/admin', data).then(res => res, reject => reject);
          },
          content: [
            {component: "a-input", style:"margin-bottom:10px", placeholder: "输入用户名", key: "name"},
            {component: "a-input", placeholder: "输入邮箱", key: "email"},
          ]
        }
      },
      detail: ""
    };
  },
  mounted: async function () {
    this.getAdmins();

    let res = await this._http.get(`/api/v3/business/actions?action_type=N`);
    let groups = res.result;
    groups = groups.map((group, groupIndex) => {
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
      return group;
    });
    console.log(groups);
    this.detail = {
      title: "操作",
      width: "1000px",
      groups,
      confirm: `
        ({record, form}, cb) => {
          let {actions,action_group} = form;
          this._http.put('/api/v3/admin-action',
            {action_group: action_group, actions: actions.filter(e => e != 'other'), admin_id: record.id}).then(cb)
        }
      `,
      show: `async (record, self) => {
        let actions = await this._http.get('/api/v3/admin-action?admin_id='+record.id);
        actions = actions.result;

        let options = self.groups.map(e => ({name: e.title, value: e.id, items: e.items}));
        options.forEach(group => {
          let selectedRowKeys = [];
          group.items.forEach(action => {
            if (actions.some(e => e == action.id)) {
              selectedRowKeys.push(action);
            }
            if (action.children) {
              action.children.forEach(action => {
                if (actions.some(e => e == action.id)) {
                  selectedRowKeys.push(action);
                }
              })
            }
          })
          group.selectedRowKeys = selectedRowKeys.map(e => e.id);
        });

        return [
          {
            component: "Form",
            content: [
              {"$eemit": "action_group", type: "selector", name: "选择功能组", key: "action_group", options:options},
              {
                type: "table",
                key: "actions",
                content: {
                  e_on: {
                    selectedRowKeys: \`$eon[action_group].selectedRowKeys\`,
                    dataSource: \`$eon[action_group].items\`,
                  },
                  selectAll: true,
                  scroll: {y: 500},
                  columns: [
                    {title: "函数编号",dataIndex:"id"},{title: "父级标签",dataIndex:"tab"},
                    {title: "访问级别",dataIndex:"_action_type"},{title: "函数类型",dataIndex:"_type"},
                    {title: "函数状态",dataIndex:"_status"}
                  ]
                }
              }
            ],
          },
        ];
      }`
    }
  },
  methods: {
    getAdmins: async function () {
      this._http.get(`/api/v3/admins`).then(res => {
        res.result.items.forEach(e => {
          e.create_time = new Date(e.create_time).toLocaleString();
          e._status = e.status ? "禁用" : "正常";
          e._role = e.role ? "超级管理员" : "普通管理员";
        })
        this.dataSource = res.result.items;
        this.pagination = {
          total: res.result.total,
          pageSize: res.result.limit || 10,
        };
      });
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
  }
}
</script>

<style lang="css" scoped>
</style>
