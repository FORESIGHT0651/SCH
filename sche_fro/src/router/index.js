import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import HomeUser from '../components/HomeUser.vue'

import AdminWelcome from '../components/AdminSystem/AdminWelcome.vue'
import Car from '../components/AdminSystem/Car.vue'
import GoodRouteSearch from '../components/AdminSystem/GoodRouteSearch.vue'
import AdUpdatePwd from '../components/AdminSystem/AdUpdatePwd.vue'
import GoodRoute from '../components/AdminSystem/GoodRoute.vue'
import GoodRoutePlan from '../components/AdminSystem/GoodRoutePlan.vue'
import LBS from '../components/AdminSystem/LBS.vue'
import GoodDist from '../components/AdminSystem/GoodDist.vue'
import RoutePlanManual from '../components/AdminSystem/RoutePlanManual.vue'

import UsUpdatePwd from '../components/UserSystem/UsUpdatePwd.vue'
import RouteDelivery from '../components/UserSystem/RouteDelivery.vue'
import GoodTrans from '../components/UserSystem/GoodTrans.vue'
import UserSearch from '../components/UserSystem/UserSearch.vue'
import UserWelcome from '../components/UserSystem/UserWelcome.vue'

import test from '../components/test.vue'

Vue.use(VueRouter)

export default new VueRouter({
 // const router = new VueRouter({
  routes:[
    {path:'/',redirect:'/login'},
    {path:'/login',component:Login},
    {
      path:'/home',
      component:Home,
      redirect:'/adminwelcome',
      children:[{path:'/adminwelcome',component:AdminWelcome},
                {path:'/car',component:Car},
                {path:'/goodroutesearch',component:GoodRouteSearch},
                {path:'/goodroute',component:GoodRoute},
                {path:'/adupdatepwd',component:AdUpdatePwd},
                {path:'/gooddist',component:GoodDist},
                {path:'/lbs',component:LBS},
                {path:'/goodrouteplan',component:GoodRoutePlan},
                {path:'/routeplanmanual',component:RoutePlanManual},
                {path:'/test',component:test},]
    },
    {
      path:'/homeuser',
      component:HomeUser,
      redirect:'/userwelcome',
      children:[{path:'/routedelivery',component:RouteDelivery},
                {path:'/goodtrans',component:GoodTrans},
                {path:'/usupdatepwd',component:UsUpdatePwd},
                {path:'/usersearch',component:UserSearch},
                {path:'/userwelcome',component:UserWelcome},]
    },
  ]
});
// //使用router。beforeEach注册一个全局前置守卫，判断用户是否登陆
// router.beforeEach((to,from,next) =>{
//   if(to.path === '/login'){
//     next();
//   }else{
//     let token = localStorage.getItem('Authorization');
//     if(token === 'null' || token === ''){
//       next('/login');
//     }else{
//       next();
//     }
//   }
// })
