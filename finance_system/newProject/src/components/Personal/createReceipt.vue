<template>
  <div>
    <el-card class="top-box-card">
      <div slot="header" class="clearfix">
        <span>基本信息</span>
      </div>
      <el-form
        style="float:left width:100%"
        :model="receiptForm"
        :rules="rules"
        ref="receiptForm"
        class="demo-ruleForm"
      >
        <el-form-item
          style="float:left width:100%"
          label="报销名称"
          prop="reason"
        >
          <el-input v-model="receiptForm.reason"></el-input>
        </el-form-item>
        <el-form-item style="float:left width:100%" label="备注" prop="note">
          <el-input type="textarea" v-model="receiptForm.note"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>费用明细</span>
        <el-button
          type="text"
          icon="el-icon-refresh-right"
          @click="getAllInvoiceData()"
        ></el-button>
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="addFromMyInvoice"
          >从我的发票里导入</el-button
        >
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="AddNONinvoice"
          >手动添加凭证</el-button
        >
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="AddTripinvoice"
          >自动识别车票</el-button
        >
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="AddMyinvoice"
          >自动识别发票</el-button
        >
      </div>
      <el-table :data="allInvoicesData" style="width: 100%">
        <el-table-column prop="invoiceKind" label="发票类型" width="180">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="开票时间"
          width="180"
          :formatter="dateFormatter"
        >
        </el-table-column>
        <el-table-column prop="kind" label="消费分类" width="180">
        </el-table-column>
        <el-table-column prop="cost" label="报销金额" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.cost | rounding }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="tax" label="税额" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.tax | rounding }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="states" label="" width="180"> </el-table-column>
        <el-table-column prop="address" label="操作">
          <template slot-scope="scope">
            <el-button @click="editInvoice(scope.row)" type="text" size="small"
              >修改</el-button
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
      <el-drawer
        title="我的发票"
        size="50%"
        :visible.sync="drawer"
        :before-close="handleClose"
        :append-to-body="true"
      >
        <el-table
          ref="multipleTable"
          :data="allDrawerData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="发票类型:">
                  <span>{{ props.row.invoiceKind }}</span>
                </el-form-item>
                <el-form-item label="报销种类:">
                  <span>{{ props.row.kind }}</span>
                </el-form-item>
                <el-form-item label="开票时间:" :formatter="dateFormatter">
                  <span>{{ props.row.createTime }}</span>
                </el-form-item>
                <el-form-item label="报销金额:">
                  <span>{{ props.row.cost | rounding}}</span>
                </el-form-item>
                <el-form-item label="税额:">
                  <span>{{ props.row.tax | rounding}}</span>
                </el-form-item>
                <el-form-item label="税率:">
                  <span>{{ props.row.taxRate | rounding}}</span>
                </el-form-item>
                <el-form-item label="发票备注:">
                  <span>{{ props.row.note }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>

          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column
            property="invoiceKind"
            label="发票类型"
            width="100"
          ></el-table-column>
          <el-table-column
            property="createTime"
            label="开票时间"
            width="100"
            :formatter="dateFormatter"
          ></el-table-column>
          <el-table-column property="cost" label="报销金额">
            <template slot-scope="scope">
              <span>{{ scope.row.cost | rounding }}</span>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
          <el-button @click="toggleSelection()">取消选择</el-button>
          <el-button @click="handleIntro()">导入</el-button>
        </div>
      </el-drawer>
      <div>
        <AddMy_invoice
          ref="AddMyinvoice"
          v-on:Getdata="GetData"
        ></AddMy_invoice>
      </div>
      <div>
        <AddNONinvoice
          ref="AddNONinvoice"
          v-on:Getdata="GetData"
        ></AddNONinvoice>
      </div>
      <div>
        <AddTrip_invoice
          ref="AddTripinvoice"
          v-on:Getdata="GetData"
        ></AddTrip_invoice>
      </div>
      <div>
        <EditMy_invoice
          ref="EditMyinvoice"
          v-on:Getdata="GetData"
        ></EditMy_invoice>
      </div>
      <div>
        <EditNONinvoice
          ref="EditNONinvoice"
          v-on:Getdata="GetData"
        ></EditNONinvoice>
      </div>
      <div>
        <EditTrip_invoice
          ref="EditTrip_invoice"
          v-on:Getdata="GetData"
        ></EditTrip_invoice>
      </div>
    </el-card>
    <!--
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>差旅</span>
      </div>
      <el-descriptions :column="1" :model="receiptForm">
        <el-descriptions-item
          label="差旅补助"
          prop="subsidyCost"
          v-model.number="receiptForm.subsidyCost"
          >{{ this.receiptForm.subsidyCost }}元</el-descriptions-item
        >
        <el-descriptions-item
          label="总金额"
          prop="totalCost"
          v-model.number="receiptForm.totalCost"
          >{{ this.receiptForm.totalCost }}元</el-descriptions-item
        >
        <el-descriptions-item
          label="总税金"
          prop="totalTax"
          v-model="receiptForm.totalTax"
          >{{ this.receiptForm.totalTax }}元</el-descriptions-item
        >
      </el-descriptions>
    </el-card>
    -->
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>汇总</span>
      </div>
      <el-descriptions :column="1" :model="receiptForm">
        <el-descriptions-item
          label="差旅补助"
          prop="subsidyCost"
          v-model.number="receiptForm.subsidyCost"
          >{{ this.receiptForm.subsidyCost | rounding }}元</el-descriptions-item
        >
        <el-descriptions-item
          label="总金额"
          prop="totalCost"
          v-model.number="receiptForm.totalCost"
          >{{ this.receiptForm.totalCost | rounding }}元</el-descriptions-item
        >
        <el-descriptions-item
          label="总税金"
          prop="totalTax"
          v-model="receiptForm.totalTax"
          >{{ this.receiptForm.totalTax | rounding }}元</el-descriptions-item
        >
      </el-descriptions>
    </el-card>
    <div>
      <el-button
        style="float: right; margin: 10px 20px; padding-right: 20px"
        type="primary"
        size="medium"
        @click="updateReceipts('check', 'receiptForm', 1)"
        >提交</el-button
      >
      <el-button
        style="float: right; margin: 10px 0px; padding-right: 20px"
        type="primary"
        size="medium"
        @click="updateReceipts('nosubmit', 'receiptForm', 0)"
        >保存</el-button
      >
    </div>
  </div>
</template>

<script>
import AddMy_invoice from "../myInvoice/My_invoiceComponents/AddMy_invoice.vue";
import AddNONinvoice from "../myInvoice/NONinvoiceComponents/AddNONinvoice.vue";
import AddTrip_invoice from "../myInvoice/Trip_invoiceComponents/AddTrip_invoice.vue";
import EditMy_invoice from "../myInvoice/My_invoiceComponents/EditMy_invoice.vue";
import EditNONinvoice from "../myInvoice/NONinvoiceComponents/EditNONinvoice.vue";
import EditTrip_invoice from "../myInvoice/Trip_invoiceComponents/EditTrip_invoice.vue";
export default {
  name: "create-component",
  data() {
    return {
      page: 1,
      usr: 0,
      pageSize: 100,
      drawer: false,
      cardColumn: "1",
      checkStatus: 0, //保存和提交按钮的验证状态
      checkMessage: "", //保存和提交按钮的验证成功的提示信息
      allInvoicesData: [],
      invoicesData: [],
      nonInvoicesData: [],
      tripInvoicesData: [],
      multipleSelection: [],
      allDrawerData: [],
      invoicesDrawerData: [],
      nonDrawerData: [],
      tripDrawerData: [],
      receiptForm: {
        reason: "",
        note: "",
        totalCost: 0,
        totalTax: 0,
        subsidyCost: 0,
        tripCost: 0,
      },
      rules: {
        reason: [
          { required: true, message: "请输入报销事由", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    AddMyinvoice() {
      this.$refs.AddMyinvoice.showAdd();
      this.$refs.AddMyinvoice.AddReceipts(this.$route.query.id);
    },
    AddNONinvoice() {
      this.$refs.AddNONinvoice.showAdd();
      this.$refs.AddNONinvoice.AddReceipts(this.$route.query.id);
    },
    AddTripinvoice() {
      this.$refs.AddTripinvoice.showAdd();
      this.$refs.AddTripinvoice.AddReceipts(this.$route.query.id);
    },
    addFromMyInvoice() {
      this.drawer = true;
      this.getDrawerData();
    },
    EditMyinvoice(row) {
      this.$refs.EditMyinvoice.showEdit(row);
    },
    EditNONinvoice(row) {
      this.$refs.EditNONinvoice.showEdit(row);
    },
    EditTrip_invoice(row) {
      this.$refs.EditTrip_invoice.showEdit(row);
    },
    editInvoice(row) {
      if (row.type == 1) {
        this.EditMyinvoice(row);
      } else if (row.type == 2) {
        this.EditNONinvoice(row);
      } else if (row.type == 3) {
        this.EditTrip_invoice(row);
      }
    },
    //从报销单中删除发票：将发票对应的报销单改为空
    deleteInvoice(row) {
      this.$post({
        url: "/api/invoice/updateInvoice",
        data: {
          invoiceId: row.invoiceId,
          invoiceReceiptsId: "",
        },
      }).then((response) => {
        this.getAllInvoiceData(); //刷新页面
      });
    },
    deleteNoInvoice(row) {
      this.$post({
        url: "/api/noInvoice/updatenoInvoice",
        data: {
          noninvoiceId: row.noninvoiceId,
          invoiceReceiptsId: 0,
        },
      }).then((response) => {
        this.getAllInvoiceData(); //刷新页面
      });
    },
    deleteTripInvoice(row) {
      this.$post({
        url: "/api/tripInvoice/updateTripInvoice",
        data: {
          tripInvoiceId: row.tripInvoiceId,
          invoiceReceiptsId: 0,
        },
      }).then((response) => {
        this.getAllInvoiceData(); //刷新页面
      });
    },
    handleDeleteClick(row) {
      this.$confirm("此操作将移出该发票, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          if (row.type == 1) {
            this.deleteInvoice(row);
          } else if (row.type == 2) {
            this.deleteNoInvoice(row);
          } else if (row.type == 3) {
            this.deleteTripInvoice(row);
          }
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    GetData() {
      this.$post({
        url: "/api/invoice/queryInvoice",
        data: {},
      }).then((response) => {
        this.tableData = response.data;
      });
    },
    //格式化时间
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
    //初始化报销单所属的发票表
    initData() {
      this.allInvoicesData = [];
      this.invoicesData = [];
      this.nonInvoicesData = [];
      this.tripInvoicesData = [];
    },
    //发票列表数据获取
    getAllInvoiceData() {
      this.initData(); //初始化报销单所属的发票表
      this.$post({
        url: "/api/invoice/queryInvoice",
        data: {
          page: this.page,
          pageSize: this.pageSize,

          invoiceReceiptsId: this.$route.query.id,
        },
      }).then((response) => {
        this.invoicesData = response.data.records;
        for (var i = 0; i < this.invoicesData.length; i++) {
          this.invoicesData[i]["type"] = 1;
        }
        this.allInvoicesData = this.allInvoicesData.concat(this.invoicesData);
        this.$post({
          url: "/api/noInvoice/queryNoInvoice",
          data: {
            page: this.page,
            pageSize: this.pageSize,

            invoiceReceiptsId: this.$route.query.id,
          },
        }).then((response) => {
          this.nonInvoicesData = response.data.records;
          for (var i = 0; i < this.nonInvoicesData.length; i++) {
            this.nonInvoicesData[i]["invoiceKind"] = "凭证";
            this.nonInvoicesData[i]["type"] = 2;
          }
          this.allInvoicesData = this.allInvoicesData.concat(
            this.nonInvoicesData
          );
          this.$post({
            url: "/api/tripInvoice/queryTripInvoice",
            data: {
              page: this.page,
              pageSize: this.pageSize,

              invoiceReceiptsId: this.$route.query.id,
            },
          }).then((response) => {
            this.tripInvoicesData = response.data.records;
            for (var i = 0; i < this.tripInvoicesData.length; i++) {
              this.tripInvoicesData[i]["invoiceKind"] = "车票/差旅凭证";
              this.tripInvoicesData[i]["type"] = 3;
            }
            this.allInvoicesData = this.allInvoicesData.concat(
              this.tripInvoicesData
            );
            this.receiptForm.totalCost = 0;
            this.receiptForm.totalTax = 0;
            for (var i = 0; i < this.allInvoicesData.length; i++) {
              this.receiptForm.totalCost += this.allInvoicesData[i].cost;
              this.receiptForm.totalTax += this.allInvoicesData[i].tax;
            }
          });
        });
      });
    },
    //抽屉中无报销单的发票列表获取
    getDrawerData() {
      this.allDrawerData = []; //初始化抽屉数据
      this.$post({
        url: "/api/invoice/queryInvoice",
        data: {
          page: this.page,
          pageSize: this.pageSize,
          usrId: this.usr,
          invoiceReceiptsId: "",
        },
      }).then((response) => {
        this.invoicesDrawerData = response.data.records;
        for (var i = 0; i < this.invoicesDrawerData.length; i++) {
          this.invoicesDrawerData[i]["type"] = 1;
        }
        this.allDrawerData = this.allDrawerData.concat(this.invoicesDrawerData);
        this.$post({
          url: "/api/noInvoice/queryNoInvoice",
          data: {
            page: this.page,
            pageSize: this.pageSize,
            usrId: this.usr,
            invoiceReceiptsId: 0,
          },
        }).then((response) => {
          this.nonDrawerData = response.data.records;
          for (var i = 0; i < this.nonDrawerData.length; i++) {
            this.nonDrawerData[i]["invoiceKind"] = "凭证";
            this.nonDrawerData[i]["type"] = 2;
          }
          this.allDrawerData = this.allDrawerData.concat(this.nonDrawerData);
          this.$post({
            url: "/api/tripInvoice/queryTripInvoice",
            data: {
              page: this.page,
              pageSize: this.pageSize,
              usrId: this.usr,
              invoiceReceiptsId: 0,
            },
          }).then((response) => {
            this.tripDrawerData = response.data.records;
            for (var i = 0; i < this.tripDrawerData.length; i++) {
              this.tripDrawerData[i]["invoiceKind"] = "车票/差旅凭证";
              this.tripDrawerData[i]["type"] = 3;
            }
            this.allDrawerData = this.allDrawerData.concat(this.tripDrawerData);
          });
        });
      });
    },
    //获取当前报销单信息
    getReceiptData() {
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          invoiceReceiptsId: this.$route.query.id,
          pageSize: 1,
          page: 1,
        },
      }).then((response) => {
        this.receiptData = response.data.records;
        this.receiptForm.reason = response.data.records[0].title;
        this.receiptForm.note = response.data.records[0].userNote;
        this.receiptForm.totalCost = response.data.records[0].totalCost;
        this.receiptForm.totalTax = response.data.records[0].taxSum;
        this.receiptForm.subsidyCost = response.data.records[0].subsidyCost;
      });
    },
    //获取差旅补助金额
    getSubsidyCost() {
      this.$post({
        url: "/api/subsidy/findSubsidy",
        data: {
          invoiceReceiptsId: this.$route.query.id,
          pageSize: 1,
          page: 1,
        },
      }).then((response) => {
        this.receiptForm.subsidyCost = response.data.records[0].cost;
      });
    },
    //判断点击保存和提交按钮时表单是否合理
    getCheckStatus(state, totalCost) {
      if (state == 0) {
        this.checkStatus = 1;
        this.checkMessage = "保存成功!";
      } else if (state == 1 && totalCost != 0) {
        this.checkStatus = 1;
        this.checkMessage = "提交成功!";
      }
    },
    //保存或者提交报销单
    updateReceipts(address, formName, state) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.getCheckStatus(state, this.receiptForm.totalCost);
          if (this.checkStatus) {
            this.getSubsidyCost();
            this.$post({
              url: "/api/receipts/updateReceipts",
              data: {
                invoiceReceiptsId: this.$route.query.id,
                states: state,
                userNote: this.receiptForm.note,
                title: this.receiptForm.reason,
                totalCost: this.receiptForm.totalCost,
                taxSum: this.receiptForm.totalTax,
                subsidyCost: this.receiptForm.subsidyCost,
              },
            }).then((response) => {
              this.$router.push({ name: address });
              this.$store.commit("isNotShow", true);
              this.$message({
                type: "success",
                message: this.checkMessage,
              });
            });
          } else {
            this.$message({
              type: "warning",
              message: "请添加发票!",
            });
          }
        } else {
          return false;
        }
      });
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleIntro() {
      this.$confirm("是否修改选中记录的状态?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //遍历获得多选选中的index值
        this.multipleSelection.forEach((value, index) => {
          //遍历多选框获取的数据
          this.allDrawerData.forEach((v, i) => {
            //遍历数据表，任意一个属性值相同，则说明该数据被选中，其i则为索引值
            if (value.type == 1) {
              if (value.involceId == v.invoiceId) {
                this.$post({
                  url: "/api/invoice/updateInvoice",
                  data: {
                    invoiceId: value.invoiceId,
                    invoiceReceiptsId: this.$route.query.id,
                  },
                }).then((response) => {});
              }
            } else if (value.type == 2) {
              if (value.noninvoiceId == v.noninvoiceId) {
                this.$post({
                  url: "/api/noInvoice/updatenoInvoice",
                  data: {
                    noninvoiceId: value.noninvoiceId,
                    invoiceReceiptsId: this.$route.query.id,
                  },
                }).then((response) => {});
              }
            } else if (value.type == 3) {
              if (value.tripInvoiceId == v.tripInvoiceId) {
                this.$post({
                  url: "/api/tripInvoice/updateTripInvoice",
                  data: {
                    tripInvoiceId: value.tripInvoiceId,
                    invoiceReceiptsId: this.$route.query.id,
                  },
                }).then((response) => {});
              }
            }
          });
        });
        this.getAllInvoiceData();
        this.drawer = false;
        this.$message({
          type: "success",
          message: "导入成功!",
        });
      });
    },
    getUsr() {
      this.$post({
        url: "/api/queryUser",
        data: {
          userName: this.$store.state.currentUser,
        },
      }).then((response) => {
        this.usr = response.data[0].usrId;
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
    this.getReceiptData();
    this.getAllInvoiceData();
  },
  computed: {},
  components: {
    AddMy_invoice,
    AddNONinvoice,
    AddTrip_invoice,
    EditMy_invoice,
    EditNONinvoice,
    EditTrip_invoice,
  },
};
</script>

<style scoped>
.top-box-card {
  position: relative;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-left: 20px;
  margin-bottom: 0;
  width: 50%;
}

.el-form-item__label {
  color: #99a9bf;
}
.el-form--label-left .el-form-item__label {
  color: #99a9bf;
}
</style>
