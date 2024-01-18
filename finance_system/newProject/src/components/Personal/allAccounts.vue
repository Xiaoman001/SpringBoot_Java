<template>
  <div>
    <div class="scope-input">
      <el-input
        placeholder="请输入内容"
        v-model="input"
        class="input-with-select"
      >
        <el-select v-model="select" slot="prepend" placeholder="请选择">
          <el-option label="报销单名称" value="1"></el-option>
        </el-select>
        <el-button slot="append" icon="el-icon-search" @click="getScopeData()"></el-button>
        
      </el-input>
      <el-button type="primary" @click="inputReset()">重置</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%" height="525">
        <el-table-column
        prop="invoiceReceiptsId"
        label="报销单编号"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="title"
        label="报销单名称"
        width="120"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="报销日期"
        min-width="150"
        align="center"
        :formatter="dateFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="states"
        label="报销单状态"
        width="120"
        align="center"
        :formatter="forStates"
      >
      </el-table-column>
      <el-table-column
        prop="totalCost"
        label="报销金额"
        width="120"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.totalCost | rounding }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="taxSum"
        label="税金"
        width="120"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.taxSum | rounding }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="subsidyCost"
        label="差旅补助"
        width="120"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.subsidyCost | rounding }}</span>
        </template>
      </el-table-column>
        <el-table-column
          prop="address"
          label="操作"
          fixed="right"
          align="center"
        >
          <template slot-scope="scope">
            <el-button
              @click="handleExamineClick(scope.row, 'examineRe')"
              type="text"
              size="small"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @current-change="handleCurrentChange"
        :page-size="this.pageSize"
        :pager-count="9"
        :current-page="page"
        layout="total, prev, pager, next, jumper"
        :total="this.total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      page: 1,
      select: "",
      input: "",
      pageSize: 7,
      total: 0,
      currentPage: 1,
      tableData: [],
    };
  },
  methods: {
    getData() {
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          usrId: this.usr,
          page: this.page,
          pageSize: this.pageSize,
        },
      }).then((response) => {
        this.tableData = response.data.records;
        this.total = response.data.total;
      });
    },
    getScopeData(){
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          usrId: this.usr,
          page: this.page,
          pageSize: this.pageSize,
          title:this.input,
        },
      }).then((response) => {
        this.tableData = response.data.records;
        this.total = response.data.total;
      });
    },
    inputReset(){
      this.getUsr();
      this.input = "";
    },
    // 切换分页
    handleCurrentChange: function (currentPage) {
      this.page = currentPage; //点击第几页
      this.getData();
    },
    forStates(row, column) {
      if (row.states != null) {
        switch (row.states) {
          case "0":
            return "未发起";
            break;
          case "1":
            return "待部门经理审批";
            break;
          case "2":
            return "待财务人员审批";
            break;
          case "3":
            return "待总经理审批";
            break;
          case "4":
            return "已完成";
            break;
          case "5":
            return "异常";
            break;
        }
      }
    },
    dateFormatter(row, column) {
      let datetime = row.createTime;
      if (datetime) {
        datetime = new Date(datetime);
        let y = datetime.getFullYear() + "-";
        let mon = datetime.getMonth() + 1 + "-";
        let d = datetime.getDate();
        let h = datetime.getHours() + ":";
        let m = datetime.getMinutes() + ":";
        let s = datetime.getSeconds();
        return y + mon + d + " " + h + m + s;
      }
      return "";
    },
    handleExamineClick(row, address) {
      this.$router.push({ name: address });
      this.$store.commit("isNotShow", false);
      this.$store.commit("getReimbursement", row.invoiceReceiptsId);
      this.$store.commit("getApprovalType", 1);
    },
    getUsr() {
      this.$post({
        url: "/api/queryUser",
        data: {
          userName: this.$store.state.currentUser,
        },
      }).then((response) => {
        this.usr = response.data[0].usrId;
        this.getData();
      });
    },
  },
  //金额保留小数点后两位
  filters: {
    rounding(value) {
      if (value == null) {
        value = 0;
      }
      return value.toFixed(2);
    },
  },
  mounted() {
    this.getUsr();
  },
  components: {},
};
</script>

<style scoped>
.scope-input {
  width: 850px;
}
.el-input {
  width: 650px;
}
.el-input .el-select {
  width: 150px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.scope-input {
  position: relative;
  top: 10px;
  left: 10px;
}
.el-table {
  position: relative;
  top: 10px;
}
.el-pagination {
  position: absolute;
  bottom: 0;
  left: 50%;
  height: 50px;
  transform: translateX(-50%);
}
</style>