import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
import Login from '@/components/login'
import personView from '@/components/Personal/personView'
import myInvoice from '@/components/myInvoice/invoice'
import approval from '@/components/approvalComponents/app'
import setting from '@/components/settingComponents/setting'
import message from '@/components/messageComponents/messages'
import developer from '@/components/newwork/l1'
import comment from '@/components/newwork/c1'
Vue.use(Router)

const  router= new Router({
  mode:'history',
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/index',
      redirect: '/personView/checking'
    },
    {
      path: '/index',
      name:'index',
      component: Index,
      children:[
        {
          path:'/personView',
          name:'personView',
          component:personView,
          children:[
            {
              path: 'checking',
              name: 'check',
              component: ()=>import('@/components/Personal/checkingForm')
            },
            {
              path: 'all',
              name: 'all',
              component: ()=>import('@/components/Personal/allAccounts')
            },
            {
              path: 'nosubmit',
              name: 'nosubmit',
              component: ()=>import('@/components/Personal/nonForm')
            },
            {
              path: 'finished',
              name: 'finished',
              component: ()=>import('@/components/Personal/finishedForm')
            },
            {
              path: 'abnormal',
              name: 'abnormal',
              component: ()=>import('@/components/Personal/abnormalForm')
            },
            {
              path: 'createRe',
              name: 'createRe',
              component: ()=>import('@/components/Personal/createReceipt')
            },
            {
              path: 'examineRe',
              name: 'examineRe',
              component: ()=>import('@/components/approvalComponents/Approval')
            },

          ]
        },
        {
          path:'/myInvoices',
          name:'invoice',
          component:myInvoice
        },
        {
          path:'/approval',
          name:'approval',
          component:approval,
          children:[
            {
              path:'pending',
              name:'pending',
              component:()=>import('@/components/approvalComponents/PendingTable'),
            },
            {
              path:'history',
              name:'history',
              component:()=>import('@/components/approvalComponents/History'),
            },
            {
              path:'appr',
              name:'appr',
              component:()=>import('@/components/approvalComponents/Approval'),
            },
          ]
        },
        {
          path:'/settings',
          name:'setting',
          component:setting
        },
        {
          path:'/develop',
          name:'developer',
          component:developer
        },
        {
          path:'/comment',
          name:'comment',
          component:comment
        },
        {
          path:'/messages',
          name:'message',
          component:message
        },
        {
          path:'/questionQuery',
          name:'question',
          component:()=>import('@/components/questionQuery/Query'),
          children:[
            {
              path:'queryInvoice',
              name:'queryInvoice',
              component:()=>import('@/components/questionQuery/queryInvoice'),
            }
          ]
        },

      ]
    }
  ]
})
router.beforeEach((to,from,next)=>{
  if(to.path == '/login'){
    localStorage.clear()
    next();
  }else{
    if(localStorage.getItem('token')){
      next()
    }
    else{
      next('/login');
    }
  }
})
export default router
