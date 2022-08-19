import Vue from 'vue'
import './plugins/axios'
import VueSession from 'vue-session'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
//导入字体图标
import './assets/fonts/iconfont.css'
//import axios from 'axios'
import store from './store'
import echarts from 'echarts'
import Vuex from 'vuex'
import md5 from 'js-md5';

Vue.prototype.$md5 = md5;
//Vue.prototype.$axios = axios
Vue.use(Vuex)
Vue.config.productionTip = false
Vue.use(VueSession)
Vue.prototype.$echarts = echarts

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


