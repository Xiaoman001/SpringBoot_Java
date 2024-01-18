<template>
  <div id="setting">
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
          @click="currentPage='1'">BASE地配置</el-menu-item
        >
        <el-menu-item class="menu" index="2"
          @click="currentPage='2'">差旅补助配置</el-menu-item
        >
        <el-menu-item class="menu" index="3"
          @click="currentPage='3'">报销种类配置</el-menu-item
        >
        <el-menu-item class="menu" index="4"
          @click="currentPage='4'">税务信息配置</el-menu-item
        >

      </el-menu>
      </el-header>

      <el-main>
        <BaseConfig v-show="currentPage=='1'"></BaseConfig>
        <InvoiceKindConfig v-show="currentPage=='3'"></InvoiceKindConfig>
        <SubsidyConfig v-show="currentPage=='2'"></SubsidyConfig>
        <TaxConfig v-show="currentPage=='4'"></TaxConfig>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import BaseConfig from "./BaseConfig";
import InvoiceKindConfig from "./InvoiceKindConfig";
import SubsidyConfig from "./SubsidyConfig";
import TaxConfig from "./TaxConfig";

export default {
  name: "ConfigPage",
  components: {TaxConfig, SubsidyConfig, InvoiceKindConfig, BaseConfig},
  data() {
    return {
      currentPage:"1"
    };
  },
  methods: {
  }
}

</script>
<style scoped>
#setting{
  position: absolute;
  width: 100%;
  min-height: 600px;
  flex: auto;
}
.el-menu-demo {
  height: 36px;
}
.el-menu--horizontal > .el-menu-item {
  height: 36px;
  line-height: 36px;
}
</style>
