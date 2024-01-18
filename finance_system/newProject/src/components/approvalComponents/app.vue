<template>
 <div class="main">
    <div id="page1">
      <el-menu
        :default-active="activeIndex2"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#409EFF"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item class="menu" index="1"
          @click="changeItem('pending')">待审批区</el-menu-item
        >
        <el-menu-item class="menu" index="2"
          @click="changeItem('history')">审批历史</el-menu-item
        >
       
      </el-menu>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "page1",
  components: {},
  data() {
    return {
      activeIndex: "1",
      activeIndex2: "1",
    };
  },
  methods: {
    handleSelect(key, keyPath) {
    },
    changeItem(address){
      this.$router.push({name:address})
    }
  },
};
</script>

<style>
a {
  text-decoration: none;
}

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