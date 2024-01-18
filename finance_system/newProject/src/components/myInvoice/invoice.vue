<template>
  <div id="myInvoice">
    <el-container >
      <el-header style="height:40px">
        <el-menu
        :default-active="currentPage"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#409EFF"
        text-color="#fff"
        active-text-color="#ffd04b"
        v-model="currentPage"
      >
        <el-menu-item class="menu" index="1"
          @click="currentPage='1'">增值税发票</el-menu-item
        >
        <el-menu-item class="menu" index="2"
          @click="currentPage='2'">行程票</el-menu-item
        >
        <el-menu-item class="menu" index="3"
          @click="currentPage='3'">非发票</el-menu-item
        >
      </el-menu>
      </el-header>

      <el-main >
        <tripInvoice v-show="currentPage=='2'"></tripInvoice>
        <My_Invoice v-show="currentPage=='1'"></My_Invoice>
        <NON_Invoice v-show="currentPage=='3'"></NON_Invoice>
      </el-main>
  </el-container>
  </div>
</template>

<script>
import tripInvoice from "./tripInvoice";
import My_Invoice from "./My_Invoice";
import NON_Invoice from "./NON_Invoice";
export default {
  components: {tripInvoice,My_Invoice,NON_Invoice},
  data() {
    return {
      currentPage:'1'
    };
  },
  methods: {
  }

}
</script>

<style>
#myInvoice{
  position: absolute;
  width: 100%;
  min-height: 600px;
}
.el-menu-demo {
  height: 36px;
}
.el-menu--horizontal > .el-menu-item {
  height: 36px;
  line-height: 36px;
}
</style>
