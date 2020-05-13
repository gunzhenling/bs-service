<template>
  <a-layout id="components-layout-demo-top-side-2">
    <a-layout-header class="header">
      <!-- <div class="logo" ></div> -->
      <a-menu theme="dark" mode="horizontal" :defaultSelectedKeys="[0]" :style="{ lineHeight: '64px' }" >
        <a-menu-item :key="0">超级管理员</a-menu-item>
      </a-menu>
      <span :style="{ lineHeight: 'none' }">
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
      </span>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff;text-align:left;">
        <a-menu mode="inline"  :selectedKeys="selectedKeys">
          <a-menu-item key="user" @click="selectedKeys = ['user'];$router.push('/admin/user')">
            <span>管理员</span>
          </a-menu-item>
          <a-menu-item key="online" @click="selectedKeys = ['online'];$router.push('/admin/online')">
            <span>线上功能</span>
          </a-menu-item>
          <a-menu-item key="offline" @click="selectedKeys = ['offline'];$router.push('/admin/offline')">
            <span>功能审核</span>
          </a-menu-item>
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
    let user = "";
    try {
      user = JSON.parse(localStorage.user);
    } catch (e) {

    }
    if (!user) {
      window.location.href = "/login";
    }
    let path = this.$route.path.split("/");
    return {
      user,
      selectedKeys: [path[path.length - 1]]
    };
  },
  mounted: async function () {
  },
  methods: {
    logout () {
      localStorage.user = "";
      this.$router.push('/login');
    },
    modifyPwd () {
      this.$router.push('/admin/password');
    },
  }
}
</script>

<style>
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
  width: 100%;
  padding: 0 24px 24px;
  background:white;
  margin:0 20px;
  min-height: calc(100vh - 64px);
}
</style>
