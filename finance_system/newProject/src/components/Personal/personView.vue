<template>
  <div id="personView">
    <el-menu
      :default-active="defaultIndex"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#409EFF"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item class="menu" index="1" @click="changeItem('check')"
        >审批中</el-menu-item
      >
      <el-menu-item class="menu" index="2" @click="changeItem('nosubmit')"
        >未发起</el-menu-item
      >
      <el-menu-item class="menu" index="3" @click="changeItem('finished')"
        >已完成</el-menu-item
      >
      <el-menu-item class="menu" index="4" @click="changeItem('abnormal')"
        >异常</el-menu-item
      >
      <el-menu-item class="menu" index="5" @click="changeItem('all')"
        >全部</el-menu-item
      >
    </el-menu>
    <div
      style="float: right; margin: 10px 0; padding-right: 10px"
      v-if="this.$store.getters.notCreateInfo"
    >
      <el-button type="primary" size="small" @click="createRec('createRe')"
        >+新建报销单</el-button
      >
    </div>
    <div>
      <router-view></router-view>
    </div>
    
  </div>
</template>

<script>
export default {
  name: "personView",
  data() {
    return {
      defaultIndex: "1",
      index: "",
      invoiceReceiptsId:'',
      usr:0,
    };
  },
  methods: {
    changeItem(address) {
      this.$router.push({ name: address });
      this.$store.commit("isNotShow", true);
    },
    createItem(address) {
      this.$router.push({ name: address });
      this.$store.commit("isNotShow", false);
    },
    createRec(address) {
      this.$post({
        url: "/api/receipts/addReceipts",
        data: {
          usrId: this.usr,
          states: '0',
          createTime: new Date(),
        },
      }).then((response) => {
        this.$store.commit("isNotShow", false);
        this.invoiceReceiptsId = response.data;
        this.$router.push({ name: address , query:{id:this.invoiceReceiptsId}});
        this.$message({
          type:'success',
          message: "新建报销单成功!",
        });
      });
    },
    getUsr(){
      this.$post({
        url: "/api/queryUser",
        data: {
          userName:this.$store.state.currentUser,
        },
      }).then((response) => {
        this.usr = response.data[0].usrId
      });
    }
  },
  mounted(){
    this.getUsr();
  }
};
</script>

<style>
#personView {
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

#personView .el-divider--horizontal {
  margin: 10px 0;
}
.account-create-button1 {
  right: 10px;
}
a {
  text-decoration: none;
}

.el-menu-demo {
  height: 36px;
}
.el-menu--horizontal > .el-menu-item {
  height: 36px;
  line-height: 36px;
}
</style>