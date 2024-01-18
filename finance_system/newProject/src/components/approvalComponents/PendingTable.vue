<template>
  <div class="pending_approval">
    <el-table
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      max-height="500"
      stripe
    >
      <el-table-column
        prop="createTime"
        label="报销日期"
        width="200"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="name" label="报销人" width="100" align="center">
      </el-table-column>
      <el-table-column
        prop="title"
        label="报销单名称"
        width="160"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="totalCost"
        label="报销金额"
        width="160"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.totalCost | rounding }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="taxSum" label="税额" width="160" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.taxSum | rounding }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="taxExclud"
        label="价税合计"
        width="160"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.taxExclud | rounding }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="state"
        label="报销单状态"
        show-overflow-tooltip
        align="center"
      >
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link to="appr"
            ><el-button @click="handleClick(scope.row)" type="text" size="small"
              >查看</el-button
            ></router-link
          >
          <el-button @click="handleAdopt(scope.row)" type="text" size="small"
            >通过</el-button
          >
          <el-button @click="handleVisible(scope.row)" type="text" size="small"
            >驳回</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 20px" align="center">
      <el-pagination
        @current-change="handleCurrentChange"
        :page-size="this.pageForm.size"
        layout="total, prev, pager, next, jumper"
        :total="this.pageForm.total"
      >
      </el-pagination>
    </div>
    <!--驳回意见窗口-->
    <el-dialog
      title="驳回"
      :visible.sync="dialogFormVisible"
      :append-to-body="true"
    >
      <el-form>
        <el-form-item label="审批意见">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="textarea"
          >
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleReject()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "pending",
  data() {
    return {
      dialogFormVisible: false,
      textarea: "",
      pageForm: {
        total: 0,
        size: 8,
        page: 1,
      },
      rejectRow: [],
      Condition: [],
      tableData: [],
    };
  },
  computed: {
    CurrentName() {
      return this.$store.state.currentName;
    },
  },
  mounted() {
    this.GetCondition();
  },
  //金额保留小数点后两位
  filters: {
    rounding(value) {
      return value.toFixed(2);
    },
  },
  methods: {
    //分页
    handleCurrentChange(val) {
      this.pageForm.page = val;
      if (this.Condition[0].grant == "1") {
        this.GetData(this.Condition[0].department, "1");
      }
      //权限为3可能是财务部门普通员工，财务部门经理，总经理
      else if (this.Condition[0].grant == "3") {
        //是财务部门经理要能看到财务部门states为1的报销单
        if (this.Condition[0].role == "部门经理") {
          this.GetData("财务", "1");
        } else if (this.Condition[0].role == "总经理") {
          this.GetData(null, "3");
        } else {
          this.GetData(null, "2");
        }
      }
    },

    GetCondition() {
      this.$post({
        url: "/api/queryUser",
        data: {
          name: this.CurrentUser,
        },
      }).then((res) => {
        //this.tableData[i].userName = res.data.name;
        this.Condition = res.data;
        //console.log(this.Condition[0].grant);
        //权限为1必然是某部门经理
        if (this.Condition[0].grant == "1") {
          this.GetData(this.Condition[0].department, "1");
        }
        //权限为3可能是财务部门普通员工，财务部门经理，总经理
        else if (this.Condition[0].grant == "3") {
          //是财务部门经理要能看到财务部门states为1的报销单
          if (this.Condition[0].role == "部门经理") {
            this.GetData("财务", "1");
          } else if (this.Condition[0].role == "总经理") {
            this.GetData(null, "3");
          } else {
            this.GetData(null, "2");
          }
        }
      });
    },
    //查询第一页
    GetData(department, state) {
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          page: this.pageForm.page,
          pageSize: this.pageForm.size,
          states: state,
          department: department,
        },
      }).then((response) => {
        this.pageForm.total = response.data.total;
        this.tableData = response.data.records;
        //console.log(response.data.records);
        for (var i = 0; i < this.tableData.length; i++) {
          if (response.data.records[i].createTime != null) {
            this.tableData[i].createTime = this.$options.methods.dateFormat(
              response.data.records[i].createTime
            );
          }
          this.tableData[i].state = this.$options.methods.statesFormat(
            response.data.records[i].states
          );
          this.tableData[i].taxExclud =
            parseFloat(response.data.records[i].totalCost) +
            parseFloat(response.data.records[i].taxSum);
        }
      });
    },
    handleClick(row) {
      this.$store.commit("getReimbursement", row.invoiceReceiptsId);
      this.$store.commit("getApprovalType", 0);
    },
    //通过
    handleAdopt(row) {
      this.$post({
        url: "/api/receipts/updateReceipts",
        data: {
          invoiceReceiptsId: row.invoiceReceiptsId,
          usrId: row.usrId,
          title: row.title,
          states: parseInt(row.states) + 1 + "",
          approver: row.approver + this.CurrentName + " ",
        },
      }).then((response) => {
        //console.log(response);
        location.reload();
        if (response.data) {
          this.$message({
            type: "success",
            message: "通过成功!",
          });
        } else {
          this.$message({
            type: "error",
            message: "出错了",
          });
        }
      });
    },
    handleVisible(row) {
      this.dialogFormVisible = true;
      this.rejectRow = row;
      //console.log(this.rejectRow);
    },
    //驳回
    handleReject() {
      this.dialogFormVisible = false;
      this.$post({
        url: "/api/receipts/updateReceipts",
        data: {
          invoiceReceiptsId: this.rejectRow.invoiceReceiptsId,
          usrId: this.rejectRow.usrId,
          title: this.rejectRow.title,
          approver: this.rejectRow.approver + this.CurrentName + " ",
          approvalNote: this.textarea,
          states: "5",
        },
      }).then((response) => {
        //console.log(response);
        location.reload();
        //appStatus[this.userForm.statesNum].sta = "error";
        if (response.data) {
          this.$message({
            type: "success",
            message: "驳回成功!",
          });
        } else {
          this.$message({
            type: "error",
            message: "出错了",
          });
        }
      });
    },
    statesFormat(state) {
      if (state == "0") {
        return "未发起";
      } else if (state == "1") {
        return "待部门经理审批";
      } else if (state == "2") {
        return "待财务人员审批";
      } else if (state == "3") {
        return "待总经理审批";
      } else if (state == "4") {
        return "通过";
      } else if (state == "5") {
        return "异常";
      }
    },
    dateFormat(time) {
      var a = time;
      var date = new Date(a); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + "-";
      var M =
        (date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1) + "-";
      var D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate() + " ";
      var H =
        date.getHours() < 10 ? "0" + date.getHours() : date.getHours() + ":";
      var MI =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      // var S = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds() + ' ';
      return Y + M + D + " " + H + MI;
    },
  },
};
</script>

<style scoped>
.el-button--text,
a {
  color: #66b1ff;
}
.el-pagination {
  position: absolute;
  bottom: 0;
  left: 50%;
  height: 50px;
  transform: translateX(-50%);
}
</style>
