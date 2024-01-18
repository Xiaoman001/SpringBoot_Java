<template>
  <div class="history">
    <div class="history-search">
      <div class="history-search-inner">
        <el-input
          style="width: 160px"
          placeholder="搜索报销单"
          prefix-icon="el-icon-search"
          v-model="input_name"
        >
        </el-input>
     <!--   <el-input
          style="width: 160px"
          placeholder="搜索日期"
          suffix-icon="el-icon-date"
          v-model="input_date"
        >
        </el-input>-->
        <el-button type="primary" size="small" style="margin-left: 10px" @click="serachByInput()"
          >搜索</el-button
        >
        <el-button type="info" size="small" @click="serachReset()"
          >重置</el-button
        >
      </div>
    </div>
    <el-table
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      :header-cell-style="{ 'text-align': 'center' }"
      max-height="450"
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
        prop="states"
        label="报销单状态"
        show-overflow-tooltip
        align="center"
      >
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120" align="center">
        <template slot-scope="scope">
          <router-link to="appr"
            ><el-button @click="handleClick(scope.row)" type="text" size="small"
              >查看</el-button
            ></router-link
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
  </div>
</template>

<script>
export default {
  name: "history",
  data() {
    return {
      dialogFormVisible: false,
      input_name: "",
      input_date: "",
      pageForm: {
        total: 0,
        size: 7,
        page: 1,
        title: "",
        userNote: "",
      },
      tableData: [],
    };
  },
  computed: {
    CurrentName() {
      return this.$store.state.currentName;
    },
  },
  created() {
    this.GetData();
  },
  mounted() {},
  //金额保留小数点后两位
  filters: {
    rounding(value) {
      if (value != null) {
        return value.toFixed(2);
      } else {
        return null;
      }
    },
  },
  methods: {
    handleCurrentChange(val) {
      //console.log(`当前页: ${val}`);
      this.pageForm.page = val;
      this.GetData();
    },
    //查询第一页
    GetData() {
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          page: this.pageForm.page,
          pageSize: this.pageForm.size,
          approval: this.CurrentName,
          title: this.pageForm.title,
          //createTime:"2022-01-06T11:59:20.000+00:00"
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
          this.tableData[i].states = this.$options.methods.statesFormat(response.data.records[i].states)
          this.tableData[i].taxExclud =
            parseFloat(response.data.records[i].totalCost) +
            parseFloat(response.data.records[i].taxSum);
        }
      });
    },
    handleClick(row) {
      this.$store.commit("getReimbursement", row.invoiceReceiptsId);
      this.$store.commit("getApprovalType", 1);
    },

    // 查询按钮触发事件
    serachByInput() {
      this.pageForm.title = this.input_name;
      this.GetData();
    },
    serachReset() {
      this.input_name = "";
      this.pageForm.title = "";
      this.GetData();
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
      if (time != null) {
        var a = time;
        var date = new Date(a); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + "-";
        var M =
          (date.getMonth() + 1 < 10
            ? "0" + (date.getMonth() + 1)
            : date.getMonth() + 1) + "-";
        var D =
          date.getDate() < 10 ? "0" + date.getDate() : date.getDate() + " ";
        var H =
          date.getHours() < 10 ? "0" + date.getHours() : date.getHours() + ":";
        var MI =
          date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        //var S = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return Y + M + D + " " + H + MI;
      } else {
        return null;
      }
    },
  },
};
</script>

<style scoped>
.history-search {
  background-color: white;
  height: 80px;
}
.history-search-inner {
  position: absolute;
  top: 60px;
  margin-left: 10px;
}
a {
  color: #66b1ff;
}
.el-pagination {
  position: absolute;
  bottom: 0;
  left:50%;
  height: 50px;
  transform: translateX(-50%);
}

</style>
