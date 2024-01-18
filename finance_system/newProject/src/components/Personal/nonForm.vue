<template>
  <div>
    <el-table :data="tableData" stripe style="width: 100%" height="525">
      <el-table-column
        prop="invoiceReceiptsId"
        label="报销单编号"
        width="180"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="title"
        label="报销单名称"
        width="180"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="报销日期"
        min-width="200"
        align="center"
        :formatter="dateFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="states"
        label="报销单状态"
        width="180"
        align="center"
        :formatter="forStates"
      >
      </el-table-column>
      <el-table-column
        prop="totalCost"
        label="报销金额"
        width="180"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="address" label="操作" fixed="right" align="center" min-width="180">
        <template slot-scope="scope">
          <el-button
            @click="handleUpdateClick(scope.row, 'createRe')"
            type="text"
            size="small"
            >修改</el-button
          >
          <el-button
            @click="handleSubmitClick(scope.row, 'check')"
            type="text"
            size="small"
            >提交</el-button
          >
          <el-button
            @click="handleDeleteClick(scope.row)"
            type="text"
            size="small"
            >删除</el-button
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
      usr:0,
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
          page: this.page,
          pageSize: this.pageSize,
          states: "0",
          usrId:this.usr,
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
      if ((row.states = "0")) {
        return "未发起";
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
    handleDeleteClick(row) {
      this.$confirm("此操作将永久删除该报销单, 请检查是否为空报销单，确认是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          if(row.totalCost != 0){
            this.$post({
            url: "/api/receipts/deleteReceipts",
            data: {
              id: row.invoiceReceiptsId,
            },
          }).then((response) => {
            this.getData(); //刷新页面
          });
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          }else{
            this.$message({
            type: "warning",
            message: "报销单中有相关发票，请先移出!",
          });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    handleUpdateClick(row, address) {
      this.$router.push({
        name: address,
        query: { id: row.invoiceReceiptsId },
      });
      this.$store.commit("isNotShow", false);
    },
    handleSubmitClick(row, address) {
      this.$confirm("此操作将提交审批, 请检查报销单信息，确认是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          if (row.totalCost != 0 && row.title != 0) {
            this.$post({
              url: "/api/receipts/updateReceipts",
              data: {
                invoiceReceiptsId: row.invoiceReceiptsId,
                states: "1",
              },
            }).then((response) => {
              this.$router.push({ name: address });
              this.$message({
                type: "success",
                message: "提交成功!",
              });
            });
          } else {
            this.$message({
              type: "warning",
              message: "请检查是否填写报销名称和添加发票!",
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
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