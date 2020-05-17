<template>
  <div class="">
    <Title content="订单管理"/>
    <div style="text-align:left">
      共 {{total}} 条
    </div>
    <a-table :pagination="pagination" :loading="t_loading" :rowKey="(e,i) => i" :columns="columns" bordered :data-source="list" @change="changePage">
      <template slot="oprate" slot-scope="text, record, index">
        <!-- <a-button v-if="record.order_status=='未付款'" :loading="loading" @click="add(record)">编辑</a-button>
        <a-button v-if="record.order_status=='已取消'" :loading="loading" @click="add(record)">编辑</a-button> -->
        <a-button v-if="record.order_status=='未发货'" :loading="loading" @click="e=>changeStatus(record, 1)">发货</a-button>
        <a-button v-if="record.order_status=='已发货'" :loading="loading" @click="e=>changeStatus(record, 2)">用户已收货</a-button>
        <!-- <a-button v-if="record.order_status=='已收货'" :loading="loading" @click="add(record)">编辑</a-button> -->
      </template>
      <template slot="pic" slot-scope="text, record, index">
        <img :src="record.pic" style="width:100px;height:100px;">
      </template>
      <template slot="_custom_made" slot-scope="text, record, index">
        <div v-html="text"> </div>
      </template>
    </a-table>
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
        {dataIndex: "create_time", title: "创建时间", width: '120px'},
        {dataIndex: "order_no", title: "订单号", width: '120px'},
        {dataIndex: "gift_name", title: "礼品名称"},
        {dataIndex: "pic", title: "图片", scopedSlots: {customRender: "pic"}, custom: "pic"},
        {dataIndex: "specification", title: "规格"},
        {dataIndex: "_custom_made", title: "定制", scopedSlots: {customRender: "_custom_made"}, custom: "_custom_made", width: '120px'},
        {dataIndex: "gift_amount", title: "数量"},
        {dataIndex: "buyer_pay_amount", title: "实付"},
        {dataIndex: "address", title: "收货人信息", scopedSlots: {customRender: "_custom_made"}, custom: "_custom_made", width: '200px'},
        {dataIndex: "order_status", title: "状态"},
        // {dataIndex: "limit_num", title: "定制至少售卖数量"},
        {dataIndex: "oprate", title: "操作", scopedSlots: {customRender: "oprate"}, custom: "oprate"}
      ]
    };
  },
  mounted: async function () {
    let res = await this._http.get(`/api/bs/gift/get/giftTypes`);
    this.types = res.result;
    this.form = [
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
      {key: "specification", name: "礼品规格", type: "inputs"},
      {key: "custom_made", name: "礼品定制", type: "inputs", options: this.custom_made},
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
      let res = await this._http.get(`/api/bs/order/get/list`, {offset: (this.page-1)*this.limit, limit: this.limit});
      this.t_loading = false;
      res.result.data.forEach((item, i) => {
        let user_address_json = JSON.parse(item.user_address_json);
        item.address = `收货人：${user_address_json.name}</br>联系方式：${user_address_json.phone}</br>地址：${user_address_json.province+user_address_json.city+user_address_json.district+user_address_json.address}`;
        item.pic = formatImg(item.picture);
        item.specification = JSON.parse(item.specification).standards;
        item.custom_made = JSON.parse(item.custom_made);
        item._custom_made = `${item.custom_made.made_type==0 ? "标品" : "定制"}</br>版费${item.custom_made.b_fee}</br>定制费${item.custom_made.made_fee}</br>工期${item.custom_made.prod_date}`
        item.gift_name = `(${item.gift_code})${item.gift_name}`;


        if (item.pay_status == 0) {
          item.order_status = "未付款";
        } else if (item.pay_status == 1) {
          item.order_status = "已取消";
        } else if (item.pay_status == 2) {
          if (item.ship_status == 0) {
            item.order_status = "未发货";
          } else if (item.ship_status == 1) {
            item.order_status = "已发货";
          } else if (item.ship_status == 2) {
            item.order_status = "已收货";
          }
        }
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
    add (record) {
      this.show = true;
      this.record = record;
      this.formData = [];
    },
    change (e) {
      this.formData = e;
    },
    changeStatus: async function (record, ship_status) {
      this.loading = true;
      let res = await this._http.post(`/api/bs/order/update/shipStatus`, {order_no: record.order_no, ship_status});
      this.loading = false;
      this.page = 1;
      this.getData();
    }
  }
}
</script>

<style lang="less" scoped>
</style>
