import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
//引入 echarts
import * as echarts from 'echarts'
//注册组件
Vue.prototype.$echarts = echarts
import axios from "axios";
import VueRouter from 'vue-router';
import router from './router';
import store from './store';
// import AMap from 'vue-amap';
// Vue.use(AMap);
// // 初始化vue-amap
// AMap.initAMapApiLoader({
//   // 高德的key
//   key: '406ecda731aeb0ac862be1773a3f47d5',
//   // 插件集合 （插件按需引入）
//   plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType',
//     'AMap.PolyEditor', 'AMap.CircleEditor', 'AMap.DistrictSearch','AMap.CircleMarker','AMap.Polyline'
//     // 'AMap.Object3DLayer', 'AMap.Object3D'
//   ]
// });
// //高德的安全密钥
// window._AMapSecurityConfig = {
//   securityJsCode: '0f90f32f577932d220f1b90a76ef7f00',
// }

Vue.prototype.$axios=axios;
Vue.prototype.$httpUrl='http://localhost:8090'
Vue.config.productionTip = false
//Vue.use(ElementUI);
Vue.use(VueRouter);
Vue.use(ElementUI,{size:'small'});
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
