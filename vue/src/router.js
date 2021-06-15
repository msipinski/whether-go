import Vue from 'vue'
import VueRouter from 'vue-router'
import Register from '@/views/Register'
import Home from '@/views/Home'
import Login from '@/views/Login'

Vue.use(VueRouter)

let routes = [
  {
    path: '/',
    component: Home,
  },
  {
    path: '/login',
    component: Login,
  },
  {
    path: '/register',
    component: Register,
  },
]

let router = new VueRouter({
  routes,
})

export default router
