<template>
  <div class="filters">
    <div v-for="(item,index) in values" :key="index">
      <div class="filters-item" v-if="item.type === 'selector'">
        <span>{{item.name}}</span>
        <a-select :value="item.value == '' ? 'all' : item.value" :disabled="item.disabled" style="min-width: 80px" @change="e => changeValue(index, e == 'all' ? '' : e)">
          <a-select-option v-for="(option,index) in item.options" :key="index" :disabled="option.disabled" :value="option.value">{{option.name}}</a-select-option>
        </a-select>
      </div>
      <div class="filters-item" v-if="item.type === 'checkbox'">
        <span>{{item.name}}</span>
        <a-checkbox-group v-bind="item" style="min-width: 80px" @change="e => changeValue(index, e == 'all' ? '' : e)"/>
      </div>
      <div class="filters-item" v-if="item.type === 'input'">
        <span v-if="!item.keys">{{item.name}}</span>
        <span v-if="item.keys">搜索</span>
        <span>
          <a-select v-if="item.keys" :value="item.key" style="min-width:80px;margin-right:5px;"
            @change="e => changeInputKeys(index,e)">
            <a-select-option v-for="(option,index) in item.keys" :key="index" :disabled="option.disabled" :value="option.value">{{option.name}}</a-select-option>
          </a-select>
        </span>
        <a-input :placeholder="item.placeholder" v-model="item._value" @pressEnter="e => changeValue(index, item._value)"/>
      </div>
      <div class="filters-item" v-if="item.type == 'link-select'">
        <span>{{item.name}}</span>
        <LinkSelector :dataSource="item.options" :showAll="item.showAll" :value="item.value" @change="e => changeValue(index, e == 'all' ? '' : e)"/>
      </div>

      <div class="filters-item" v-if="item.type == 'date-date'">
        <span>{{item.name}}</span>
        <a-date-picker @change="e => changeValue(index, e)" />
      </div>
      <div class="filters-item" v-if="item.type == 'date-month'">
        <span>{{item.name}}</span>
        <a-month-picker @change="e => changeValue(index, e)" />
      </div>
      <div class="filters-item" v-if="item.type == 'date-range'">
        <span>{{item.name}}</span>
        <a-range-picker @change="e => changeValue(index, e && e.length && e || '')" />
      </div>
      <!-- NOTE 无类型即是默认的神策copy的时间筛选 -->
      <div class="filters-item" v-if="item.type == undefined">
        <span>{{item.name}}</span>
        <a-popconfirm placement="bottomLeft" @confirm="dateTableConfirm(index)" cancelText="" okText="">
          <span slot="icon"></span>
          <template slot="title">
            <div class="date-table">
              <span class="left">
                <template v-for="dateRangeItem in dateRangeItems">
                  <span v-if="dateRangeItem instanceof Array">
                    <a-button :type="dateRange.name == name && 'primary' || 'default'"
                      :disabled="item.disabledItems && item.disabledItems.indexOf(name) != -1"
                      v-for="name in dateRangeItem" :key="name" @click="changeRangeItem(index, name)">{{name}}</a-button>
                  </span>
                  <a-button :type="dateRange.name == dateRangeItem && 'primary' || 'default'"
                    :disabled="item.disabledItems && item.disabledItems.indexOf(dateRangeItem) != -1"
                    v-else @click="changeRangeItem(index, dateRangeItem)">{{dateRangeItem}}</a-button>
                </template>
              </span>
              <DateRange :value="dateRange.value" :disabled="item.disabledItems && item.disabledItems.indexOf('时间范围') != -1" @change="changeRangeDate"/>
            </div>
          </template>
          <a-button v-if="!item._name" type="default">时间范围筛选</a-button>
          <a-button v-else type="default">{{item._name}}</a-button>
        </a-popconfirm>
      </div>
      <!-- <div class="filters-item" v-if="item.type == 'date-week'">
        <span>{{item.name}}</span>
        <a-week-picker @change="e => changeValue(index, e)" />
      </div> -->
    </div>
  </div>
</template>

<script>
import LinkSelector from "./common/LinkSelector.vue";
import DateRange from "./common/DateRange.vue";
export default {
  name: "Filters",
  components: {LinkSelector, DateRange},
  props: ["content", "id", "page", "limit"],
  watch: {
    content (newV) {
      this.init(newV);
      return newV;
    }
  },
  data () {
    return {
      values: [],
      dateRange: {},
      dateRangeItems: [
         ['昨日', '今日'],
         ['上周', '本周'],
         ['上月', '本月'],
         ['去年', '本年'],
         '过去七天',
         '过去30天',
         '上线至今',
      ],
      // data: [
      //   {name: "创建时间", type: "date-date"},
      //   {name: "创建时间", type: "date-month"},
      //   {name: "创建时间", type: "date-week"},
      //   {name: "创建时间", type: "date-range"},
      //   {name: "状态1", type: "selector", options: [1,2,3,4].map(e => ({name: `name${e}`, value: `value${e}`}))},
      //   {name: "状态1", type: "selector", options: [{name:"请选择",value:"all"},{name: "名称", placeholder: "请输入中文",value: "name"}, {name: "手机1111sdfasd",value:"mobile"}]},
      //   {key: "mobile", type: "input",  keys: [{name: "名称", placeholder: "请输入中文",value: "name"}, {name: "手机1111sdfasd",value:"mobile"}]},
      //   {name: "分类", type: "link-select", dataSource: [
      //     {name: `name${1}`, value: 1, children: [ {name: `name${11}`,value:11, children: [ {name: `name${112}`,value:112}, {name: `name${1121}`,value:1121}, ]}, {name: `name${111}`,value:111}, ]},
      //     {name: `name${2}`, value: 2, children: [ {name: `name${22}`,value:22, children: [ {name: `name${222}`,value:222}, {name: `name${2222}`,value:2222}, ]}, {name: `name${222}`,value:222}, ]},
      //     {name: `name${3}`, value: 3, children: [ {name: `name${33}`,value:33}, {name: `name${333}`,value:333}, ]},
      //   ]},
      // ]
    }
  },
  mounted () {
    this.init(this.content);
  },
  methods: {
    init (content) {
      console.log(content);
      let obj = {}
      this.values = content.map(e => {
        if (e.type == "selector") {
          e.value = e.options[0].value;
        } else if (e.type == "input" && e.keys) {
          e.key = e.keys[0].value;
          e.placeholder = e.keys[0].placeholder;
        } else if (e.type == undefined) {
          if (e.value) {
            e._name = `${this.$formatDate(e.value[0])} 至 ${this.$formatDate(e.value[1])}`
            this.dateRange = {name: e._name,value: [`${e.value[0]} 00:00:00`, `${e.value[1]} 23:59:59`]};
          }
        }

        if (e.type == "link-select" && e.value) {
          obj[e.key] = e.value;
        } else if (e.type == "selector") {
          obj[e.key] = e.value != "all" ? e.value : "";
        } else if (e.type == "checkbox") {
          obj[e.key] = e.value;
        } else if (e.value != undefined) {
          obj[e.key] = [`${e.value[0]} 00:00:00`, `${e.value[1]} 23:59:59`];
        }
        return e;
      });
      console.log(this.values);

      if (this.id) {
        this.$nextTick(() => {// NOTE: 延时触发，让后面的代码渲染完
          this.$e_emit(this.id, {...obj, page: this.page || 1, limit: this.limit || 10});
        })
      }
    },
    changeValue (index, e) {
      if (index != undefined) {
        console.log(index,e);
        this.values[index].value = e;
        this.values = [].concat(this.values);
      }
      let obj = {}
      this.values.forEach(e => {
        if (!e.key) return;
        if (e.type == "link-select" && e.value) {
          obj[e.key] = e.value;
        } else if (e.type == "selector") {
          obj[e.key] = e.value != "all" ? e.value : "";
        } else if (e.value != undefined) {
          obj[e.key] = e.value;
        }
      });
      if (this.id) {
        this.$e_emit(this.id, {...obj, page: this.page || 1, limit: this.limit || 10});
      } else {
        console.log({...obj, page: this.page || 1, limit: this.limit || 10});
        this.$emit("change", {...obj, page: this.page || 1, limit: this.limit || 10});
      }
    },
    changeInputKeys (index, e) {
      let item = this.values[index];
      item.key = e;
      item.placeholder = item.keys[item.keys.findIndex(e => e.value == item.key)].placeholder;
    },
    changeRangeDate (e) {
      this.dateRange = {name: e && `${this.$formatDate(e[0])} 至 ${this.$formatDate(e[1])}`, value: e};
    },
    changeRangeItem (index, value) {
      // NOTE: 选中可取消
      // if (this.dateRange.name == value) {
      //   this.dateRange = {};
      //   return;
      // }
      let nowTime = new Date().getTime();
      let today = this.$formatDate(nowTime);
      let year = Number(today.split("-")[0]);
      let month = Number(today.split("-")[1]);
      let yesterday = this.$formatDate(nowTime-86400000);
      let day = new Date().getDay();
      let preWeekStartDay = this.$formatDate(nowTime - (day + 6) * 86400000);// NOTE: 上周一
      let preWeekEndDay = this.$formatDate(nowTime - day * 86400000);// NOTE: 上周日
      let nowWeekStartDay = this.$formatDate(nowTime - (day - 1 == -1 ? 6 : day - 1) * 86400000);// NOTE: 上周日
      let preMonthStartDay = this.$formatDate(new Date().setFullYear(year, month-2, 1));// NOTE: 上个月开始
      let preMonthEndDay = this.$formatDate(new Date().setFullYear(year, month-2, new Date(new Date().setFullYear(year, month-2, 0)).getDate()));// NOTE: 上个月结束
      let nowMonthStartDay = this.$formatDate(new Date().setFullYear(year, month-1, 1));// NOTE: 本月开始
      let preYear = this.$formatDate(new Date().setFullYear(year-1, 0, 1));// NOTE: 本月开始
      let nowYear = this.$formatDate(new Date().setFullYear(year, 0, 1));// NOTE: 本月开始
      let pre7Day = this.$formatDate(nowTime - 7 * 86400000);// NOTE: 本月开始
      let pre30Day = this.$formatDate(nowTime - 30 * 86400000);// NOTE: 本月开始
      const data = {
        "昨日": [`${yesterday} 00:00:00`, `${yesterday} 23:59:59`],
        "今日": [`${today} 00:00:00`, `${today} 23:59:59`],
        "上周": [`${preWeekStartDay} 00:00:00`, `${preWeekEndDay} 23:59:59`],
        "本周": [`${nowWeekStartDay} 00:00:00`, `${today} 23:59:59`],
        "上月": [`${preMonthStartDay} 00:00:00`, `${preMonthEndDay} 23:59:59`],
        "本月": [`${nowMonthStartDay} 00:00:00`, `${today} 23:59:59`],
        "去年": [`${preYear} 00:00:00`, `${nowYear} 00:00:00`],
        "本年": [`${nowYear} 00:00:00`, `${today} 23:59:59`],
        "过去七天": [`${pre7Day} 00:00:00`, `${today} 23:59:59`],
        "过去30天": [`${pre30Day} 00:00:00`, `${today} 23:59:59`],
        "上线至今": undefined,
      };
      this.dateRange = {name: value, value: data[value]};
      // this.changeValue (index, data[value]);
    },
    dateTableConfirm (index) {
      this.values[index]._name = this.dateRange.name;
      this.changeValue(index, this.dateRange.value)
    }
  }
}
</script>

<style scoped lang="less">
.filters {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  .filters-item {
    display: flex;
    align-items: center;
    padding-right: 15px;
    padding-bottom: 10px;
    word-break: keep-all;
    >span:first-child {
      padding-right: 10px;
      font-weight: bold;
    }
  }
}
.date-table {
  margin-left: -22px;
  display: flex;
  .left {
    width: 200px;
    margin-right: 20px;
    >* {
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      margin-bottom: 10px;
      width: 100%;
      >* {
        flex: 1;
        &:first-child {
          margin-right: 10px;
        }
      }
    }
  }
}
</style>
