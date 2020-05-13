import Vue from 'vue'
import Router from 'vue-router'
import Layout from './views/manager/Layout.vue'
import Default from './views/manager/Default.vue'
import Password from './views/manager/Password.vue'
import Login from './views/Login.vue'
// import AdminLayout from './views/admin/Layout.vue'
// import AdminUser from './views/admin/User.vue'
// import AdminOnline from './views/admin/Online.vue'
// import AdminOffline from './views/admin/Offline.vue'
import Gift from './views/manager/Gift.vue'
import GiftNew from './views/manager/GiftNew.vue'
import Order from './views/manager/Order.vue'
import User from './views/manager/User.vue'
import Type from './views/manager/Type.vue'
import Notice from './views/manager/Notice.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: "/",
  routes: [
    {
      path: '/login',
      component: Login
    },
    {
      path: '/manager',
      component: Layout,
      children: [
        { path: '/manager/password', component: Password, },
        { path: '/manager/gift', component: Gift, },
        { path: '/manager/gift_new', component: GiftNew, },
        { path: '/manager/order', component: Order, },
        { path: '/manager/user', component: User, },
        { path: '/manager/type', component: Type, },
        { path: '/manager/notice', component: Notice, },
      ]
    },
  ]
})
