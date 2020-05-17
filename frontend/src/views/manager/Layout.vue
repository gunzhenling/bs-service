<template>
  <a-layout id="components-layout-demo-top-side-2">
    <a-layout-header class="header">
      <a-menu theme="dark" mode="horizontal" :defaultSelectedKeys="[0]" :style="{ lineHeight: '64px' }" >
        <a-menu-item :key="0">管理后台</a-menu-item>
      </a-menu>
      <!-- <span :style="{ lineHeight: 'none' }">
        <a-dropdown>
          <a class="ant-dropdown-link" href="#">
            {{user.admin.name}} <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a @click="modifyPwd">修改密码</a>
            </a-menu-item>
            <a-menu-item>
              <a @click="logout">退出登录</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span> -->
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff;text-align:left;">
        <a-menu mode="inline" :selectedKeys="[pathname]">
          <a-menu-item v-for="(item, index) in actions" v-if="!item.children" :key="item.link" @click="clickAction(item)">
            <span>{{item.title}}</span>
          </a-menu-item>
          <a-sub-menu v-for="(item, index) in actions" v-if="item.children" :key="item.link">
            <span slot="title">{{item.title}}</span>
            <a-menu-item v-for="(child, _index) in item.children" :key="index+'_'+_index" @click="clickAction(child)">{{child.title}}</a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <div class="app-container">
        <router-view/>
      </div>
    </a-layout>
  </a-layout>
</template>

<script>
export default {
  data () {
    return {
      pathname: location.pathname,
      actions: [
        {title: "用户管理", link: "/manager/user"},
        {title: "礼品列表", link: "/manager/gift"},
        // {title: "类型管理", link: "/manager/type"},
        {title: "订单列表", link: "/manager/order"},
        {title: "公告管理", link: "/manager/notice"},
      ]
    };
  },
  watch: {
    $route (e) {
      this.pathname = location.pathname
    }
  },
  mounted: async function () {
  },
  methods: {
    clickAction (action) {
      if (this.pathname == action.link ) {
        return ;
      }
      this.action = action;
      this.$router.push(action.link);
    },
    logout () {
      localStorage.user = "";
      this.$router.push('/login');
    },
    modifyPwd () {
      this.$router.push('/manager/password');
    },
  }
}
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  background: rgba(255,255,255,.2);
  margin: 16px 28px 16px 0;
  float: left;
}
.app-container {
  position: relative;
  width: calc(100% - 200px - 24px);
  padding: 0 24px 24px;
  background:white;
  margin:0 20px;
  min-height: calc(100vh - 64px);
}
</style>
