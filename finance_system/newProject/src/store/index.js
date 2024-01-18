import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
Vue.use(Vuex)

const store = new Vuex.Store({

  strict:true,  // 开启严格模式  确保state 中的数据只能 mutations 修改
  state:{
    isNotCreate:true,//个人视图不是新建状态
    currentUser:"",//当前用户账户
    currentName:"",//当前用户姓名
    reimbursement:0,//当前报销单id
    approvalType: 0,
  },
  mutations:{ // 更改数据的方法
    getUser(state,item){
      state.currentUser = item;
    },
    getReimbursement(state,item){
      state.reimbursement = item;
    },
    getCurrentName(state,item){
      state.currentName = item;
    },
    getApprovalType(state,item){
      state.approvalType = item;
    },
    isNotShow(state,payload){
        state.isNotCreate = payload
    }

    },
    getters: {
        notCreateInfo(state) {
            return state.isNotCreate//返回isNotCreate的值
        }
    },
    //解决vuex传值刷新后变成默认值的问题
    plugins: [
        createPersistedState({
            storage: window.sessionStorage
        })
    ]
})

export default store;

