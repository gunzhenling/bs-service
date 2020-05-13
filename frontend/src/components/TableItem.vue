<template>
  <Container :view="content" :record="record" :column="column" :body="body" :no_reload="no_reload" @reload="$emit('reload')"/>
</template>

<script>
export default {
  props: ["view", "record", "column", "index", "body", "no_reload"],
  beforeCreate: function () {
    this.$options.components.Container = () => import('./Container.vue')
  },
  watch: {
    record (newV) {
      this.initData(this.view, newV);
      return newV;
    },
  },
  mounted () {
    this.initData(this.view, this.record);
  },
  data () {
    return {
      html: "",
      loading: false,
      content: {},
    }
  },
  methods: {
    initData (view, record) {
      let {dataIndex} = this.column;
      let component = view.component || view;
      let content = typeof view == 'string' ? {component} : {...view};

      const loading = (loading) => {
        content.loading = loading;
        this.content.loading = loading;
        if (this.content.children && typeof this.content.children == 'object') {
          this.content.children = {...this.content.children, loading: loading};
        };
        this.content = {...this.content};
      }
      loading(false);
      content.postId = content.postId && eval("`"+content.postId+"`");
      if (component == 'img') {
        content.width = content.width || '80';
        content.height = content.height || '80';
        content.src = record[this.column.dataIndex];
      } else if (component == 'Html') {
        content.content = record[this.column.dataIndex];
      } else if (component == 'a-switch') {
        content.checkedChildren = content.checkedChildren || "开";
        content.unCheckedChildren = content.unCheckedChildren || "关";
        content.checked = record[dataIndex];
        if (content.postId) {
          content.change = async (e) => {
            loading(true);
            let res = await this._http.post(`/api/v3/business/${content.postId}`,{record, [content.key||dataIndex]: e}).then(res => {
              if (!res.code) {
                this.$emit("reload");
              } else {
                loading(false);
              }
            }, err => loading(false))
          };
        }
      }
      // console.log(JSON.parse(JSON.stringify(content)));
      this.content = content;
    }
  }
}
</script>

<style>
</style>
