<template>
  <div class="">
    <Title content="用户管理"/>
    <div style="text-align:left">
      共 {{total}} 条
    </div>
    <!-- <a-button style="float:right;margin-top:-40px;" @click="add({})">新增用户</a-button> -->
    <a-table :pagination="pagination" :loading="t_loading" :rowKey="(e,i) => i" :columns="columns" bordered :data-source="list" @change="changePage">
      <template slot="icon" slot-scope="text, record, index">
        <img :src="text" style="width:100px;height:100px;">
      </template>
    </a-table>
    <a-modal :current="page" :visible="show" :title="record&&record.id ? '编辑' : '新增用户'" width="680px" :confirmLoading="loading" @ok="confirm" @cancel="show = false">
      <div style="overflow:auto">
        <Form :content="form" :record="record" @change="change"/>
      </div>
    </a-modal>
  </div>
</template>

<script>
import Title from "../../components/Title.vue";
import Form from "../../components/Form.vue";
import Editor from "../../components/common/Editor.vue";
export default {
  components: {Title, Form},
  data () {
    // 函数编号	功能函数名	父级标签	访问级别	函数类型	函数状态	操作
    return {
      custom_made: [
        {name: "版费", value: "b_fee"},
        {name: "定制费", value: "made_fee"},
        {name: "定做工期", value: "prod_date"},
      ],
      page: 1,
      limit: 10,
      total: 0,
      pagination: {},
      list: [],
      record: {},
      form: [],

      show: false,

      loading: false,
      t_loading: false,
      types: [],
      columns: [
        {dataIndex: "name", title: "用户名"},
        {dataIndex: "icon", title: "头像", scopedSlots: {customRender: "icon"}, custom: "icon"},
        {dataIndex: "create_time", title: "注册时间"},
      ]
    };
  },
  mounted: async function () {
    this.form = [
      {key: "title", name: "标题", type:"input"},
      {key: "content", name: "内容", type:"textarea", style:"height:300px"},
    ];
    this.getData();
  },
  methods: {
    changePage (e) {
      this.page = e.current;
      this.getData();
    },
    getData: async function (){
      this.t_loading = true;
      let res = await this._http.get(`/api/bs/user/get/list`, {offset: (this.page-1)*this.limit, limit: this.limit});
      this.t_loading = false;
      this.list = res.result.data;
      this.total = res.result.total;
      this.pagination = {
        pageSize: this.limit,
        current: this.page,
        total: this.total
      }
    },
    delItem: async function (record){
      this.loading = true;
      let res = await this._http.post(`/api/bs/common/notice/delete/${record.id}`, );
      this.loading = false;
      this.page = 1;
      this.getData();
    },
    add (record) {
      this.show = true;
      this.record = record;
      this.formData = [];
    },
    change (e) {
      this.formData = e;
      console.log(e);
    },
    confirm: async function () {
      let body = {};
      (this.formData || []).forEach((e, i) => {
          body[e.key] = e.value;
      });

      if (!body.title) return this.$message.error("请输入用户标题");
      if (!body.content) return this.$message.error("请输入用户内容");
      let edit = this.record && this.record.id;
      body.id = edit;
      this.loading = true;
      let res = await this._http.post(`/api/bs/common/notice/${edit ?'update':'add'}`, body);
      this.loading = false;
      if (res && res.code) {
        this.$message.error(res.message);
      } else {
        this.$message.success(edit?'编辑用户成功':'新增用户成功');
        this.show=false;
        this.page = 1;
        this.getData();
      }
    }
  }
}
</script>

<style lang="less" scoped>
</style>
