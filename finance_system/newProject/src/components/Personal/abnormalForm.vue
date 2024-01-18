<template>
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
      <el-table-column prop="address" label="操作" fixed="right" align="center">
        <template slot-scope="scope">
          <el-button
            @click="handleExamineClick(scope.row, 'examineRe')"
            type="text"
            size="small"
            >查看</el-button
          >
          <el-button
            @click="handleWithdrawClick(scope.row)"
            type="text"
            size="small"
            >撤回</el-button
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
</template>

<script>
export default {
  name: "",
  data() {
    return {
      page: 1,
      pageSize: 7,
      total: 0,
      usr: 0,
      currentPage: 1,
      tableData: [],
    };
  },
  methods: {
    getData() {
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          page: this.page,
          pageSize: this.pageSize,
          usrId: this.usr,
          states: "5",
        },
      }).then((response) => {
        this.tableData = response.data.records;
        this.total = response.data.total;
      });
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
    handleWithdrawClick(row) {
      this.$confirm("此操作将撤回该报销申请, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$post({
            url: "/api/receipts/updateReceipts",
            data: {
              invoiceReceiptsId: row.invoiceReceiptsId,
              approvalNote: "",
              approver: "",
              states: "0",
            },
          }).then((response) => {
            this.getData(); //刷新页面
          });
          this.$message({
            type: "success",
            message: "撤销成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消撤销",
          });
        });
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