<template>
  <div class="">
    <Title content="礼品管理"/>
    <FilterView :content="filter" @change="filterChange"/>
    <div style="text-align:left"> 共 {{total}} 条 </div>
    <a-button style="float:right;margin-top:-40px;" @click="add({})">新增礼品</a-button>
    <a-table :pagination="pagination" :loading="t_loading" :rowKey="(e,i) => i" :columns="columns" bordered :data-source="list" @change="changePage">
      <template slot="oprate" slot-scope="text, record, index">
        <a-popconfirm placement="top" title="确定删除该礼品？" :okText="'确定'" :cancelText="'取消'" @confirm="delItem(record)">
          <a-button :loading="loading" type="danger">删除</a-button>
        </a-popconfirm>
        <br/>
        <a-button :loading="loading" @click="add(record)">编辑</a-button>
      </template>
      <template slot="picture_url" slot-scope="text, record, index">
        <img :src="record.picture_url" style="width:100px;height:100px;">
      </template>
    </a-table>
    <a-modal :current="page" :visible="show" :title="record&&record.gift_code ? '编辑' : '新增礼品'" width="680px" :confirmLoading="loading" @ok="confirm" @cancel="show = false">
      <div v-if="show" style="height:60vh;overflow:auto">
        <Form :content="form" :record="record" @change="change"/>
      </div>
    </a-modal>
  </div>
</template>

<script>
import Title from "../../components/Title.vue";
import Form from "../../components/Form.vue";
import FilterView from "../../components/FilterView.vue";
import Editor from "../../components/common/Editor.vue";
export default {
  components: {Title, Form,FilterView},
  data () {
    // 函数编号	功能函数名	父级标签	访问级别	函数类型	函数状态	操作
    return {
      custom_made: [
        {name: "版费", value: "b_fee"},
        {name: "定制费", value: "made_fee"},
        {name: "定做工期", value: "prod_date"},
      ],
      filter: [
        {name:"定制",type:"selector", key: "made_type", options: [
          {name: '全部',value:"all"},
          {name: '成品',value:"0"},
          {name: '定制',value:"1"},
        ]},
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
        {dataIndex: "gift_code", title: "礼品编码"},
        {dataIndex: "gift_name", title: "礼品名称"},
        {dataIndex: "picture_url", title: "图片", scopedSlots: {customRender: "picture_url"}, custom: "picture_url"},
        {dataIndex: "gift_price", title: "原始价格"},
        {dataIndex: "real_gift_price", title: "实际价格"},
        {dataIndex: "sale_num", title: "已售数量"},
        {dataIndex: "limit_num", title: "定制至少售卖数量"},
        {dataIndex: "oprate", title: "编辑", scopedSlots: {customRender: "oprate"}, custom: "oprate"}
      ]
    };
  },
  mounted: async function () {
    let res = await this._http.get(`/api/bs/gift/get/giftTypes`);
    this.types = res.result;
    this.initForm = [
      {key: "gift_code", name: "礼品编码", style:"max-width:200px"},
      {key: "gift_name", name: "礼品名称", style:"max-width:200px"},
      {key: "type_code", name: "礼品分类", type: "selector", options: [
        {name: "请选择",value: "请选择"},
        ...this.types.map(e => ({
          name: e.type_name,value: e.type_code
        }))
      ]},
      {key: "gift_price", name: "原始价格", type: "number", float: true},
      {key: "real_gift_price", name: "实际价格", type: "number", float: true},
      {key: "sale_num", name: "已售数量", type: "number", float: true},
      {key: "limit_num", name: "定制最少数量", type: "number", float: true},
      {key: "specification", name: "礼品规格", type: "inputs", options: [{name:"",value:"standards"}]},
      {key: "custom_made", name: "礼品定制", type: "inputs", options: this.custom_made, Switch: {
        0: {name: "标品",value:0},
        1: {name: "定制",value:1},
      }},
      {key: "picture_url", name: "礼品图片", type: "image", style:"max-width:200px"},
      {key: "content", name: "礼品详情", type: "editor"},
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
      let res = await this._http.get(`/api/bs/gift/get/list`, {
        made_type: this.made_type,
        offset: (this.page-1)*this.limit, limit: this.limit});
      this.t_loading = false;
      res.result.data.forEach((item, i) => {
        item.specification = JSON.parse(item.specification);
        item.custom_made = JSON.parse(item.custom_made);
        item.picture_url = formatImg(item.picture);
        console.log(item);
      });

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
      let res = await this._http.post(`/api/bs/gift/delete/${record.gift_code}`, );
      this.loading = false;
      this.page = 1;
      this.getData();
    },
    add: async function (record) {
      if (record && record.gift_code && !record.content) {
        this.loading = true;
        let res = await this._http.get(`/api/bs/gift/get/detail?gift_code=${record.gift_code}`);
        console.log(res);
        this.loading = false;
        record.content = res.result.content;
      }
      this.show = true;
      this.record = record || {};
      this.form = JSON.parse(JSON.stringify(this.initForm));
      this.formData = [];
    },
    change (e) {
      this.formData = e;
      console.log(e);
    },
    filterChange (e) {
      Object.keys(e).forEach((key, i) => {
        this[key] = e[key];
      });
      this.getData();
    },
    confirm: async function () {
      let body = {};
      console.log(this.formData );
      (this.formData || []).forEach((e, i) => {
        if (e.type == "image") {
          body[e.key] = e.value && e.value[0] ? (e.value[0].url || (e.value[0].response &&  e.value[0].response.result) || '') : undefined;
        } else {
          body[e.key] = e.value;
        }
      });

      if (!body.gift_code) return this.$message.error("请输入礼品编码");
      if (!body.gift_name) return this.$message.error("请输入礼品名称");
      if (!body.type_code || body.type_code=="请选择") return this.$message.error("请选择礼品分类");
      if (!body.gift_price) return this.$message.error("请输入原始价格");
      if (!body.real_gift_price) return this.$message.error("请输入实际价格");
      if (!body.sale_num) return this.$message.error("请输入已售数量");
      if (!body.limit_num) return this.$message.error("请输入定制最少数量");
      if (!body.specification || !body.specification.length) return this.$message.error("礼品规格不能为空");
      if (!body.specification || body.specification.some(e => [{name:"规格",value:"standards"}].some(made => !e[made.value])))  return this.$message.error("请填写完整礼品规格");
      if (!body.custom_made || !body.custom_made.length) return this.$message.error("礼品定制不能为空");
      if (!body.custom_made || body.custom_made.some(e => this.custom_made.some(made => !e[made.value])))  return this.$message.error("请填写完整礼品定制");
      if (body.picture_url == undefined) return this.$message.error("请输入礼品图片");
      if (!body.picture_url) return this.$message.error("请输入礼品图片上传成功");
      if (!body.content) return this.$message.error("请输入礼品详情");
      body.made_type = body.custom_made.length == 2 ? 1 : 0;
      let edit = this.record && this.record.gift_code;
      body.picture_url = decodeImg(body.picture_url);
      this.loading = true;
      let res = await this._http.post(`/api/bs/gift/${edit ?'update':'add'}`, body);
      this.loading = false;
      if (res && res.code) {
        this.$message.error(res.message);
      } else {
        this.$message.success(edit?'编辑礼品成功':'新增礼品成功');
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
