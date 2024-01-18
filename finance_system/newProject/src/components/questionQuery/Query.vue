<template>
  <div class="main">
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#409EFF"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item class="menu" index="1"
          @click="changeItem('queryInvoice')">发票查询</el-menu-item
        >
      </el-menu>
      <router-view></router-view>
  </div>
</template>

<script>
export default {
    data(){
        return{
            activeIndex:'1'
        } 
    },
    methods: {
    changeItem(address){
      this.$router.push({name:address})
    }
  },
}
</script>

<style>
.main {

  width: 100%;

}

.el-menu-demo {
  height: 36px;
}
.el-menu--horizontal > .el-menu-item {
  height: 36px;
  line-height: 36px;
}
</style>