<template>
  <a-locale-provider :locale="locale">
    <div id="app">
      <router-view/>
    </div>
  </a-locale-provider>
</template>

<script>
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN';
export default {
  data() {
    window.formatImg = (img) => {
      return img ? `http://localhost:9998${img.replace("/frontend/public/images", "").replace("/frontend/images", "")}` : '';
    }
    window.decodeImg = (img) => {
      return img ? img.replace(`http://localhost:9998`, "") : '';
    }
    return {
      locale: zhCN,
    }
  }
}
</script>
<style>
* {
  box-sizing: border-box;
}
body {
  font-size: 16px;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
