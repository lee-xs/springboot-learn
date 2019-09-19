import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

const routerMap = [
  {
    path: '/websocket',
    component: () => import('@/components/websocket'),
    hidden: true
  }
]

export default new Router({
  mode: 'history',
  routes: routerMap,
})
