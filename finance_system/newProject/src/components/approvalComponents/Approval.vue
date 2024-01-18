<template>
  <div id="approval">
    <!-- <div class="block">

    <el-carousel trigger="click" height="200px" style="width: 350px" :autoplay="false">
      <el-carousel-item v-for="(item, index) in list_img" :key="index">
         <el-popover placement="right" trigger="hover">
       <img :src="item.url" style="width: 580px;height: 400px">
       <img slot="reference" :src="item.url" style="width: 350px;height: 200px">
 </el-popover>
      </el-carousel-item>
    </el-carousel>
  </div>-->
    <div class="staff">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>基本信息</span>
        </div>
        <div class="text left-item">申请人：{{ userForm.name }}</div>
        <div class="text left-item">所属部门：{{ userForm.department }}</div>
        <div class="text left-item">报销单：{{ userForm.title }}</div>
        <div class="text left-item">创建时间：{{ userForm.createTime }}</div>
        <div class="textarea item">报销事由：{{ userForm.userNote }}</div>
      </el-card>
    </div>

    <div class="fin">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>费用明细</span>
        </div>
        <el-table
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%; margin-left: 1px"
          max-height="400"
          :header-cell-style="{ 'text-align': 'center' }"
        >
          <el-table-column
            prop="variety"
            label="报销类型"
            width="220"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="kind" label="用途" width="220" align="center">
          </el-table-column>
          <el-table-column
            prop="cost"
            label="报销金额"
            width="250"
            align="center"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.cost | rounding }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="tax" label="税额" width="250" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.tax | rounding }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="taxExcluded"
            label="价税合计"
            show-overflow-tooltip
            align="center"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.taxExcluded | rounding }}</span>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120"
            align="center"
          >
            <template slot-scope="scope">
              <el-button @click="openDrawer(scope.row)" type="text" size="small"
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
    <div class="total">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>汇总</span>
        </div>
        <div class="text item">
          差旅补助金额：{{ userForm.subsidyCost | rounding }}
        </div>
        <div class="text">累计汇报金额：</div>
        <div class="text item emphasis">
          {{ userForm.totalMoney | rounding }} ¥
        </div>
      </el-card>
    </div>
    <div class="total" v-show="this.Type == 0">
      <el-card class="box-card">
        <el-button @click="handleAdopt()" type="primary">通过</el-button>
        <el-button @click="dialogFormVisible = true" type="danger"
          >驳回</el-button
        >
      </el-card>
    </div>
    <div class="prograss">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>审批流程</span>
        </div>
        <div style="height: 500px">
          <el-steps
            :space="200"
            :active="1"
            direction="vertical"
            finish-status="success"
            v-show="userForm.totalMoney >= 10000"
          >
            <el-step title="提交申请" :status="appStatus[0].sta"> </el-step>
            <el-step title="部门经理审核" :status="appStatus[1].sta">
              <template slot="description">
                <div>{{ appStatus[1].op }}</div>
                <div>{{ appStatus[1].appName }}</div>
                <div>{{ appStatus[1].note }}</div>
              </template>
            </el-step>
            <el-step title="财务审核" :status="appStatus[2].sta">
              <template slot="description">
                <div>{{ appStatus[2].op }}</div>
                <div>{{ appStatus[2].appName }}</div>
                <div>{{ appStatus[2].note }}</div>
              </template>
            </el-step>
            <el-step
              title="总经理审核"
              :status="appStatus[3].sta"
            >
              <template slot="description">
                <div>{{ appStatus[3].op }}</div>
                <div>{{ appStatus[3].appName }}</div>
                <div>{{ appStatus[3].note }}</div>
              </template>
            </el-step>
            <el-step title="结束" :status="appStatus[4].sta"></el-step>
          </el-steps>

          <el-steps
            :space="200"
            :active="1"
            direction="vertical"
            finish-status="success"
            v-show="userForm.totalMoney < 10000"
          >
            <el-step title="提交申请" :status="appStatus[0].sta"> </el-step>
            <el-step title="部门经理审核" :status="appStatus[1].sta">
              <template slot="description">
                <div>{{ appStatus[1].op }}</div>
                <div>{{ appStatus[1].appName }}</div>
                <div>{{ appStatus[1].note }}</div>
              </template>
            </el-step>
            <el-step title="财务审核" :status="appStatus[2].sta">
              <template slot="description">
                <div>{{ appStatus[2].op }}</div>
                <div>{{ appStatus[2].appName }}</div>
                <div>{{ appStatus[2].note }}</div>
              </template>
            </el-step>
            <el-step title="结束" :status="appStatus[4].sta"></el-step>
          </el-steps>
        </div>
      </el-card>
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

    <!--发票详情抽屉-->
    <el-drawer
      title="费用明细"
      :visible.sync="drawer"
      :direction="direction"
      :append-to-body="true"
      size="40%"
    >
      <!--有发票抽屉-->
      <div v-show="currentVariety == '有发票'">
        <div class="abstract">
          <el-card class="box-card shrink">
            <div slot="header" class="clearfix shrink-header">
              <span>发票信息</span>
            </div>
            <div class="small-text left-item">
              发票名称：{{ invoiceForm.invoiceName }}
            </div>
            <div class="small-text left-item">
              发票类型：{{ invoiceForm.invoiceKind }}
            </div>
            <div class="small-text left-item">
              开票时间：{{ invoiceForm.invoiceTime }}
            </div>
            <div class="small-text left-item">
              发票代码：{{ invoiceForm.invoiceCode }}
            </div>
            <div class="small-text left-item">
              发票号码：{{ invoiceForm.invoiceNum }}
            </div>
            <div class="small-text left-item">
              校验码：{{ invoiceForm.checkCode }}
            </div>
            <div class="small-text left-item">
              发票用途：{{ invoiceForm.kind }}
            </div>
          </el-card>
        </div>

        <div class="el-drawer-invoice">
          <el-card class="box-card">
            <div slot="header" class="clearfix shrink-header">
              <span>文件信息</span>
            </div>
            <el-popover
              placement="right"
              trigger="hover"
              v-show="fileType == 'img'"
            >
              <img :src="imgUrl" style="height: 500px" />
              <img slot="reference" :src="imgUrl" style="height: 200px" />
            </el-popover>
            <el-popover
              placement="left"
              trigger="click"
              v-show="fileType == 'pdf'"
            >
              <div class="pdf">
                <pdf ref="pdf" :src="pdfUrl" style="width: 800px"> </pdf>
              </div>
              <el-button
                slot="reference"
                @click="pdfLoad(pdfUrlDefault)"
                size="small"
                >点击查看发票</el-button
              >
            </el-popover>
          </el-card>
        </div>

        <div class="el-drawer-receipts">
          <el-card class="box-card">
            <div slot="header" class="clearfix shrink-header">
              <span>报销信息</span>
            </div>
            <div class="text left-item">
              发票总额：{{ invoiceForm.cost | rounding }}
            </div>
            <div class="text left-item">
              税额：{{ invoiceForm.tax | rounding }}
            </div>
            <div class="text left-item">税号：{{ invoiceForm.soldTaxid }}</div>
            <div class="text left-item">
              价税合计：{{ invoiceForm.taxExcluded | rounding }}
            </div>
            <div class="textarea left-item">
              发票备注：{{ invoiceForm.note }}
            </div>
          </el-card>
        </div>

        <div class="el-drawer-invoice">
          <el-card class="box-card">
            <div class="small-text item">费用合计</div>
            <div class="text item emphasis" style="color: blue">
              {{ invoiceForm.taxExcluded | rounding }} ¥
            </div>
          </el-card>
        </div>
      </div>

      <!--无发票抽屉-->
      <div v-show="currentVariety == '无发票'">
        <div class="abstract">
          <el-card class="box-card shrink">
            <div slot="header" class="clearfix shrink-header">
              <span>凭证信息</span>
            </div>
            <div class="small-text left-item">
              名称：{{ nonInvoiceForm.noninvoiceName }}
            </div>
            <div class="small-text left-item">
              用途：{{ nonInvoiceForm.kind }}
            </div>
          </el-card>
        </div>

        <div class="el-drawer-invoice">
          <el-card class="box-card">
            <div slot="header" class="clearfix shrink-header">
              <span>文件信息</span>
            </div>
            <el-popover
              placement="right"
              trigger="hover"
              v-show="fileType == 'img'"
            >
              <img :src="imgUrl" style="height: 500px" />
              <img slot="reference" :src="imgUrl" style="height: 200px" />
            </el-popover>
            <el-popover
              placement="left"
              trigger="click"
              v-show="fileType == 'pdf'"
            >
              <div class="pdf">
                <pdf ref="pdf" :src="pdfUrl" style="width: 800px"> </pdf>
              </div>
              <el-button
                slot="reference"
                @click="pdfLoad(pdfUrlDefault)"
                size="small"
                >点击查看发票</el-button
              >
            </el-popover>
          </el-card>
        </div>

        <div class="el-drawer-receipts">
          <el-card class="box-card">
            <div slot="header" class="clearfix shrink-header">
              <span>报销信息</span>
            </div>
            <div class="text left-item">
              总额：{{ nonInvoiceForm.cost | rounding }}
            </div>
            <div class="text left-item">
              税额：{{ nonInvoiceForm.tax | rounding }}
            </div>
            <div class="text left-item">
              税率：{{ nonInvoiceForm.taxRate | rounding }}
            </div>
            <div class="text left-item">
              价税合计：{{ nonInvoiceForm.taxExcluded | rounding }}
            </div>
            <div class="textarea left-item">
              备注：{{ nonInvoiceForm.note }}
            </div>
          </el-card>
        </div>

        <div class="el-drawer-invoice">
          <el-card class="box-card">
            <div class="small-text item">费用合计</div>
            <div class="text item emphasis" style="color: blue">
              {{ nonInvoiceForm.taxExcluded | rounding }} ¥
            </div>
          </el-card>
        </div>
      </div>

      <!--车票抽屉-->
      <div v-show="currentVariety == '车票'">
        <div class="abstract">
          <el-card class="box-card shrink">
            <div slot="header" class="clearfix shrink-header">
              <span>发票信息</span>
            </div>
            <div class="small-text left-item">
              出发地：{{ tripInvoiceForm.startPlace }}
            </div>
            <div class="small-text left-item">
              目的地：{{ tripInvoiceForm.endPlace }}
            </div>
            <div class="small-text left-item">
              出发时间：{{ tripInvoiceForm.startTime }}
            </div>
            <div class="small-text left-item">
              到达时间：{{ tripInvoiceForm.endTime }}
            </div>
          </el-card>
        </div>

        <div class="el-drawer-invoice">
          <el-card class="box-card">
            <div slot="header" class="clearfix shrink-header">
              <span>文件信息</span>
            </div>
             <el-popover
              placement="right"
              trigger="hover"
              v-show="fileType == 'img'"
            >
              <img :src="imgUrl" style="height: 500px" />
              <img slot="reference" :src="imgUrl" style="height: 200px" />
            </el-popover>
            <el-popover
              placement="left"
              trigger="click"
              v-show="fileType == 'pdf'"
            >
              <div class="pdf">
                <pdf ref="pdf" :src="pdfUrl" style="width: 800px"> </pdf>
              </div>
              <el-button
                slot="reference"
                @click="pdfLoad(pdfUrlDefault)"
                size="small"
                >点击查看发票</el-button
              >
            </el-popover>
          </el-card>
        </div>

        <div class="el-drawer-receipts">
          <el-card class="box-card">
            <div slot="header" class="clearfix shrink-header">
              <span>报销信息</span>
            </div>
            <div class="text left-item">
              总额：{{ tripInvoiceForm.cost | rounding }}
            </div>
            <div class="text left-item">
              税额：{{ tripInvoiceForm.tax | rounding }}
            </div>
            <div class="text left-item">
              税率：{{ tripInvoiceForm.taxRate | rounding }}
            </div>
            <div class="text left-item">
              价税合计：{{ tripInvoiceForm.taxExcluded | rounding }}
            </div>
          </el-card>
        </div>

        <div class="el-drawer-invoice">
          <el-card class="box-card">
            <div class="small-text item">费用合计</div>
            <div class="text item emphasis" style="color: blue">
              {{ tripInvoiceForm.taxExcluded | rounding }} ¥
            </div>
          </el-card>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import pdf from "vue-pdf";
export default {
  name: "approval",
  components: {
    pdf,
  },
  data() {
    return {
      drawer: false,
      dialogFormVisible: false,
      currentVariety: "",
      textarea: "",
      //imgUrl: "http://121.43.229.173:6790/20220111105831.jpg",
      fileType: "",
      imgUrl: "",
      pdfUrl: "",
      pdfUrlDefault: "",
      direction: "rtl",
      tableData: [],
      invoiceTable: [],
      nonInvoiceTable: [],
      tripInvoiceTable: [],
      appStatus: [
        { sta: "wait", op: "", appName: "", note: "" },
        { sta: "wait", op: "", appName: "", note: "" },
        { sta: "wait", op: "", appName: "", note: "" },
        { sta: "wait", op: "", appName: "", note: "" },
        { sta: "wait", op: "", appName: "", note: "" },
      ],
      invoiceForm: {
        checkCode: "", //校验码
        cost: 0, //发票金额
        fileId: 0, //文件ID
        invoiceCode: "", //发票代码
        invoiceId: 0, //发票ID
        invoiceKind: "", //发票类型
        invoiceName: "", //发票名称
        invoiceNum: "", //发票号码
        invoiceTime: "", //开票时间
        kind: "", //用途
        note: "", //备注
        soldTaxid: "", //税号
        tax: 0, //税额
        taxExcluded: 0, //价税合计
      },
      nonInvoiceForm: {
        cost: 0,
        fileId: 0,
        kind: "", //用途
        noninvoiceId: "",
        noninvoiceName: "",
        note: "",
        tax: 0,
        taxExcluded: 0,
        taxRate: 0,
      },
      tripInvoiceForm: {
        cost: 0,
        endPlace: "", //目的地
        endTime: "", //到达时间
        fileId: 0,
        kind: "",
        startPlace: "", //出发地
        startTime: "", //出发时间
        tax: 0,
        taxExcluded: 0,
        taxRate: 0,
        tripInvoiceId: 0,
      },
      userForm: {
        usrId: "",
        name: "",
        rejecter: "",
        rejecterGrant: 0,
        department: "",
        title: "",
        createTime: "",
        approvalNote: "",
        userNote: "",
        statesNum: 0,
        updateStates: "",
        approver: "",
        totalMoney: 0,
        subsidyCost: 0,
      },
    };
  },
  computed: {
    Reimbursement() {
      return this.$store.state.reimbursement;
    },
    CurrentName() {
      return this.$store.state.currentName;
    },
    Type() {
      return this.$store.state.approvalType;
    },
  },
  mounted() {
    this.GetUserNote();
    this.GetInvoice();
    //this.pdfTask(this.pdfUrl);
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
  methods: {
    pdfLoad(pdfurl) {
      this.pdfUrl = this.$options.methods.pdfSrc(pdfurl);
    },
    pdfSrc(pdfurl) {
      //处理pdfUrl返回
      let src = pdf.createLoadingTask({
        url: pdfurl,
        //引入pdf.js字体，templ
        cMapUrl: "https://cdn.jsdelivr.net/npm/pdfjs-dist@2.5.207/cmaps/",
        cMapPacked: true,
      });
      return src;
    },
    //获取报销事由
    GetUserNote() {
      this.$post({
        url: "/api/receipts/findReceipts",
        data: {
          page: 1,
          pageSize: 1,
          invoiceReceiptsId: this.Reimbursement,
        },
      }).then((response) => {
        //console.log(response.data.records);
        this.userForm.usrId = response.data.records[0].usrId;
        this.userForm.userNote = response.data.records[0].userNote;
        this.userForm.subsidyCost = response.data.records[0].subsidyCost;
        this.userForm.createTime = this.$options.methods.dateFormat(
          response.data.records[0].createTime
        );
        this.userForm.totalMoney = response.data.records[0].totalCost;
        this.userForm.title = response.data.records[0].title;
        this.userForm.name = response.data.records[0].name;
        this.userForm.department = response.data.records[0].department;
        this.userForm.statesNum = parseInt(response.data.records[0].states);
        this.userForm.approver = response.data.records[0].approver;
        if(this.userForm.approver==null){
          this.userForm.rejecterGrant = 0;
        }else
        {var last = this.userForm.approver.split(" ");
        //console.log(last);
        this.userForm.rejecterGrant = last.length - 1;}
        //this.userForm.rejecter = last[last.length - 2];
        //console.log(this.userForm.rejecterGrant);
        //console.log(response.data.records[0].states);
        if (
          response.data.records[0].states == "2" &&
          response.data.records[0].totalCost < 10000
        ) {
          this.userForm.updateStates = "4";
        } else {
          this.userForm.updateStates = this.userForm.statesNum + 1 + "";
        }
        if (response.data.records[0].states == "5") {
          //console.log(last[this.userForm.rejecterGrant-1]);
          this.appStatus[0].sta = "success";
          this.appStatus[this.userForm.rejecterGrant].sta = "error";
          this.appStatus[this.userForm.rejecterGrant].op = "驳回";
          this.appStatus[this.userForm.rejecterGrant].appName =
            "审批人：" + last[this.userForm.rejecterGrant - 1];
          this.appStatus[this.userForm.rejecterGrant].note =
            "审批意见:" + response.data.records[0].approvalNote;
          for (var i = 0; i < this.userForm.rejecterGrant - 1; i++) {
            this.appStatus[i + 1].sta = "success";
            this.appStatus[i + 1].op = "通过";
            this.appStatus[i + 1].appName = "审批人：" + last[i];
          }
        } else {
          for (var i = 0; i < this.userForm.rejecterGrant + 1; i++) {
            this.appStatus[i].sta = "success";
            if (i) {
              this.appStatus[i].op = "通过";
              this.appStatus[i].appName = "审批人：" + last[i - 1];
            }
          }
          this.appStatus[this.userForm.rejecterGrant + 1].sta = "process";
          if (response.data.records[0].states == "4") {
            this.appStatus[4].sta = "success";
          }
        }
      });
    },

    GetInvoice() {
      this.$post({
        url: "/api/invoice/queryInvoice",
        data: {
          page: 1,
          pageSize: 100,
          invoiceReceiptsId: this.Reimbursement,
        },
      }).then((response) => {
        //console.log(response);
        this.invoiceTable = response.data.records;
        for (var i = 0; i < this.invoiceTable.length; i++) {
          this.invoiceTable[i].variety = "有发票";
        }
        this.GetNonInvoice();
      });
    },
    GetNonInvoice() {
      this.$post({
        url: "/api/noInvoice/queryNoInvoice",
        data: {
          page: 1,
          pageSize: 100,
          invoiceReceiptsId: this.Reimbursement,
        },
      }).then((response) => {
        this.nonInvoiceTable = response.data.records;
        for (var i = 0; i < this.nonInvoiceTable.length; i++) {
          this.nonInvoiceTable[i].variety = "无发票";
        }
        this.GetTripInvoice();
      });
    },
    GetTripInvoice() {
      this.$post({
        url: "/api/tripInvoice/queryTripInvoice",
        data: {
          page: 1,
          pageSize: 100,
          invoiceReceiptsId: this.Reimbursement,
        },
      }).then((response) => {
        this.tripInvoiceTable = response.data.records;
        //console.log(response.data.records);
        for (var i = 0; i < this.tripInvoiceTable.length; i++) {
          this.tripInvoiceTable[i].kind = "车票";
          this.tripInvoiceTable[i].variety = "车票";
        }
        this.tableData = this.invoiceTable.concat(
          this.nonInvoiceTable.concat(this.tripInvoiceTable)
        );
        for (var i = 0; i < this.tableData.length; i++) {
          this.tableData[i].taxExcluded =
            parseFloat(this.tableData[i].cost) +
            parseFloat(
              this.tableData[i].tax == null ? 0 : this.tableData[i].tax
            );
        }
      });
    },
    //通过
    handleAdopt() {
      this.$post({
        url: "/api/receipts/updateReceipts",
        data: {
          invoiceReceiptsId: this.Reimbursement,
          usrId: this.userForm.usrId,
          title: this.userForm.title,
          states: this.userForm.updateStates,
          approver: this.userForm.approver + this.CurrentName + " ",
        },
      }).then((response) => {
        //console.log(response);
        this.appStatus[this.userForm.statesNum].sta = "success";
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
    //驳回
    handleReject() {
      this.dialogFormVisible = false;
      this.$post({
        url: "/api/receipts/updateReceipts",
        data: {
          invoiceReceiptsId: this.Reimbursement,
          usrId: this.userForm.usrId,
          title: this.userForm.title,
          approver: this.userForm.approver + this.CurrentName + " ",
          approvalNote: this.textarea,
          states: "5",
        },
      }).then((response) => {
        //console.log(response);
        location.reload();
        //this.appStatus[this.userForm.statesNum].sta = "error";
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
    //打开抽屉
    openDrawer(row) {
      this.drawer = true;
      //this.pdfUrl = this.$options.methods.pdfSrc(this.pdfUrl);
      //console.log(row);
      this.currentVariety = row.variety;
      if (row.variety == "有发票") {
        this.invoiceForm.checkCode = row.checkCode;
        this.invoiceForm.cost = row.cost;
        this.invoiceForm.fileId = row.fileId;
        this.GetFile(row.fileId);
        this.invoiceForm.invoiceCode = row.invoiceCode;
        this.invoiceForm.invoiceId = row.invoiceId;
        this.invoiceForm.invoiceKind = row.invoiceKind;
        this.invoiceForm.invoiceName = row.invoiceName;
        this.invoiceForm.invoiceNum = row.invoiceNum;
        this.invoiceForm.invoiceTime = this.$options.methods.dateFormat(
          row.invoiceTime
        );
        this.invoiceForm.kind = row.kind;
        this.invoiceForm.note = row.note;
        this.invoiceForm.soldTaxid = row.soldTaxid;
        this.invoiceForm.tax = row.tax;
        this.invoiceForm.taxExcluded = row.taxExcluded;
      } else if (row.variety == "无发票") {
        this.nonInvoiceForm.cost = row.cost;
        this.nonInvoiceForm.fileId = row.fileId;
        this.GetFile(row.fileId);
        this.nonInvoiceForm.kind = row.kind;
        this.nonInvoiceForm.noninvoiceId = row.noninvoiceId;
        this.nonInvoiceForm.noninvoiceName = row.noninvoiceName;
        this.nonInvoiceForm.note = row.note;
        this.nonInvoiceForm.tax = row.tax;
        this.nonInvoiceForm.taxExcluded = row.taxExcluded;
        this.nonInvoiceForm.taxRate = row.taxRate;
      } else if (row.variety == "车票") {
        this.tripInvoiceForm.cost = row.cost;
        this.tripInvoiceForm.endPlace = row.endPlace; //目的地
        this.tripInvoiceForm.endTime = this.$options.methods.dateFormat(
          row.endTime
        ); //到达时间
        this.tripInvoiceForm.fileId = row.fileId;
        this.GetFile(row.fileId);
        this.tripInvoiceForm.kind = row.kind;
        this.tripInvoiceForm.startPlace = row.startPlace; //出发地
        this.tripInvoiceForm.startTime = this.$options.methods.dateFormat(
          row.startTime
        ); //出发时间
        this.tripInvoiceForm.tax = row.tax;
        this.tripInvoiceForm.taxExcluded = row.taxExcluded;
        this.tripInvoiceForm.taxRate = row.taxRate;
        this.tripInvoiceForm.tripInvoiceId = row.tripInvoiceId;
      }
    },
    GetFile(file) {
      this.$post({
        url: "/api/file/downLoadFile",

        data: {
          id: file,
        },
      }).then((response) => {
        //console.log(response.split("."));
        var len = response.split(".").length;
        if (response.split(".")[len - 1] == "pdf") {
          this.pdfUrlDefault = "http://" + response;
          //this.pdfUrl = this.$options.methods.pdfSrc(this.pdfUrlDefault);
          this.fileType = "pdf";
        } else {
          this.imgUrl = "http://" + response;
          this.fileType = "img";
        }
      });
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
<style>
.text {
  font-size: 12px;
  width: 50%;
  height: 20px;
}
.small-text {
  font-size: 8px;
  width: 50%;
  height: 20px;
}

.item {
  margin-bottom: 12px;
}

.left-item {
  margin-bottom: 12px;
  float: left;
}

.right-item {
  margin-bottom: 12px;
  float: right;
}

.textarea {
  font-size: 12px;
  width: 500px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 100%;
}
.emphasis {
  font-size: 24px;
  font-style: bold;
}

.shrink-header {
  font-size: 14px;
}
</style>
