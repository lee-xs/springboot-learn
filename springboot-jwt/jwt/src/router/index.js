import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export const routerMap = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    component: () => import('@/components/login'),
    hidden: true
  },
  {
    path: '/index',
    meta:{ requireAuth:true },
    component: () => import('@/components/index'),
    hidden: true
  },
]

export default new Router({
  mode: 'history',
  routes: routerMap,
})

