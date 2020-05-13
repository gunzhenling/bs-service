<template>
  <div class="">
    <a-select v-for="(item,index) in selectors" :key="index" :value="value[index] || 'all'" style="min-width: 80px;margin-right:5px;" @change="e => changeValue(index, e)">
      <a-select-option v-for="(option,index) in item" :key="index" :disabled="option.disabled"
        :value="option.value != undefined ? option.value : option._id != undefined ? option._id : option.id">
        {{option.name}}
      </a-select-option>
    </a-select>
  </div>
</template>

<script>
export default {
  props: {
    // NOTE: 格式[{name,value,children:dataSource}]
    dataSource: Array,
    // NOTE: showAll 为true下拉框展示全部，默认选中全部，为false展示第一个，并默认选中第一个直到最后
    showAll: {
      type: Boolean,
      default: true,
    }
  },
  watch: {
    dataSource (newV) {
      this.initData(newV);
      return newV;
    }
  },
  mounted () {
    this.initData(this.dataSource);
  },
  data () {
    return {
      selectors: [],
      value: [],
    }
  },
  methods: {
    initData (dataSource) {
      var getValues = (arr, data=[]) => {
        data.push(arr[0].value != undefined ? arr[0].value : arr[0]._id != undefined ? arr[0]._id : arr[0].id);
        if (arr[0].children) {
          getValues(arr[0].children, data);
        }
        return data;
      }
      if (!this.showAll) {
        this.value = getValues(dataSource);
      }
      this.setSelector(this.value);
    },
    setSelector (values) {
      let all;
      if (this.showAll) {
        all = [{name: "全部", value: "all"}];
      } else {
        all = [];
      }
      let arr = [all.concat(this.dataSource)];
      let data = arr[0];
      values.forEach(value => {
        let index = data.findIndex(e => (e.value != undefined ? e.value : e._id != undefined ? e._id : e.id) == value);
        if (index != -1) {
          data = data[index].children;
          if (data) {
            arr.push(all.concat(data));
          }
        }
      });
      this.selectors = arr;
    },
    changeValue (index, e) {
      this.value[index] = e;
      if (e == "all") {
        this.value = this.value.splice(0, index);
      } else {
        this.value = this.value.splice(0, index + 1);
      }
      this.$emit("change", this.value);
      this.$emit("input", this.value);
      this.setSelector(this.value);
    }
  }
}
</script>

<style>
</style>
