import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permsission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '首页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: '图书管理',
    meta: {
      title: '图书管理',
      icon: 'example',
      perms: ['book.manage']
    },
    children: [
      {
        path: 'table',
        name: '图书管理',
        component: () => import('@/views/example/table'),
        meta: {
          title: '图书管理',
          icon: 'table',
          perms: ['book.manage']
        }
      },
    ]
  },

  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/admin',
    name: '系统管理',
    meta: {
      title: '系统管理',
      icon: 'system',
      perms: ['sys']
    },
    children: [
      {
        path: 'admin',
        name: '用户管理',
        component: () => import('@/views/sys/admin'),
        meta: {
          title: '用户管理',
          icon: 'admin',
          perms: ['sys:admin']
        }
      },
      {
        path: 'log',
        name: '后台日志',
        component: () => import('@/views/sys/log'),
        meta: {
          title: '后台日志',
          icon: 'log',
          perms: ['sys:log']
        }
      },
      {
        path: 'role',
        name: '角色管理',
        component: () => import('@/views/sys/role'),
        meta: {
          title: '角色管理',
          icon: 'role',
          perms: ['sys:role']
        }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
