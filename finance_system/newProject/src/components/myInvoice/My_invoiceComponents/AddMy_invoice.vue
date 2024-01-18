<template>
  <div class="editMyinvoice">
    <el-dialog title="新建发票" left :visible.sync="NewInvoice" custom-class="DialogClass1" :append-to-body="true">
      <div class="dialog1">
        <up-load-form  ref="upload" :imageType="this.form.invoiceKind" @receiveResult="receiveResult"></up-load-form>
      </div>
      <div class="dialog2">
        <el-form :model="form" :inline="true">
          <el-form-item label="税价合计" :label-width="formLabelWidth">
            <el-input v-model="form.cost" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="税金" :label-width="formLabelWidth">
            <el-input v-model="form.tax" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="买方税号" :label-width="formLabelWidth">
            <el-input v-model="form.soldTaxid" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="分类" :label-width="formLabelWidth">
            <el-select v-model="form.kind"
                       style="width: 200px"
                       placeholder="请选择分类">
              <el-option label="差旅" value="差旅"></el-option>
              <el-option label="办公" value="办公"></el-option>
              <el-option label="其他" value="其他"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发票代码" :label-width="formLabelWidth">
            <el-input v-model="form.invoiceCode" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="发票类型" :label-width="formLabelWidth">
            <el-select v-model="form.invoiceKind"
                       style="width: 200px"
                       placeholder="请选择发票类型">
              <el-option label="增值税发票" value="发票"></el-option>
              <el-option label="手撕票" value="手撕票"></el-option>
              <el-option label="出租车票" value="出租车"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发票号码" :label-width="formLabelWidth">
            <el-input v-model="form.invoiceNum" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="开票时间" :label-width="formLabelWidth">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.invoiceTime" style="width: 200px;"></el-date-picker>
          </el-form-item>
          <el-form-item label="校验码" :label-width="formLabelWidth">
            <el-input v-model="form.checkCode" autocomplete="true"></el-input>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input
              type="textarea"
              style="width: 200px"
              :rows="2"
              placeholder="请输入内容"
              v-model="textarea">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="NewCancel">取 消</el-button>
        <el-button type="primary" @click="NewSuccess">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import UpLoadForm from '../../Forms/upload.vue';
export default {
  name: "AddMy_invoice",
  components:{UpLoadForm},
  data(){
    return{
      form: {
        invoiceId:'',
        invoiceTime: '',
        kind: '',
        cost: '',
        invoiceKind: '',
        invoiceNum: '',
        invoiceCode: '',
        checkCode: '',
        note:'',
        select:'',
        FileId:'',
        invoiceReceiptsId:'',
        tax:'',
        soldTaxid:''
      },
      textarea: '',
      NewInvoice:false,
      formLabelWidth: '120px',
      hideUpload: false,
    }
  },
  methods:{
    showAdd(){
      this.NewInvoice = true;
      this.form.invoiceTime = null;
      this.form.kind = null;
      this.form.cost = null;
      this.form.invoiceCode = null;
      this.form.invoiceKind = null;
      this.form.invoiceNum = null;
      this.form.checkCode = null;
      this.form.tax = null;
      this.form.soldTaxid = null;
      this.textarea = null;
      //this.hideUpload = 0;
    },
    AddReceipts(ID){
      this.form.invoiceReceiptsId = ID
    },
    NewSuccess(){
      this.$post({
        url:"/api/invoice/checkInvoice",
        data:{
          num: this.form.invoiceCode
        }
      })
        .then((response)=> {
          if (response.data == "有重复") {
            this.$message({
              type: 'warning',
              message: '有重复票据，添加失败'
            });
            this.NewInvoice = false;
          }
          if (response.data == "无重复") {
            this.$post({
              url:"/api/invoice/addInvoice",
              data:{
                checkCode: this.form.checkCode,
                cost: this.form.cost,
                fileId: this.$refs.upload.file_id,
                invoiceCode: this.form.invoiceCode,
                invoiceKind: this.form.invoiceKind,
                invoiceName: "string",
                invoiceNum: this.form.invoiceNum,
                invoiceTime: this.form.invoiceTime,
                kind: this.form.kind,
                note: this.textarea,
                soldTaxid: this.form.soldTaxid,
                tax: this.form.tax,
                usrId:localStorage.getItem('userId'),
                invoiceReceiptsId: this.form.invoiceReceiptsId,
              }
            })
              .then((response)=>{
                this.$emit('Getdata');
                this.NewInvoice = false;
                this.$refs.upload.handleRemove()
                this.$message({
                  type: 'success',
                  message: '新建成功!'
                });
              })
          }
        })

    },
    NewCancel(){
      this.NewInvoice = false;
      this.$message({
        message: '取消新建'
      });
    },
    receiveResult(Result){
      if(this.form.invoiceKind ==='发票'){
        this.form.invoiceTime = Result.vat_invoice_issue_date;
        this.form.cost = Result.unit_price;
        this.form.invoiceNum = Result.vat_invoice_haoma_right_side;
        this.form.invoiceCode = Result.vat_invoice_daima_right_side;
        this.form.checkCode = Result.check_code;
        this.form.tax = Result.vat_invoice_tax_total.slice(1);
        this.form.soldTaxid = Result.vat_invoice_rate_payer_id;
        let regY=new RegExp('年','g')//g代表全部
        let regM=new RegExp('月','g')//g代表全部
        let regD=new RegExp('日','g')//g代表全部
        let newMsg=JSON.stringify(this.form.invoiceTime).replace(regY,'-');
        let newMsg1=JSON.stringify(newMsg).replace(regM,'-');
        let newMsg2=JSON.stringify(newMsg1).replace(regD,'');
        this.form.invoiceTime = newMsg2.slice(7,17)
      }
      if(this.form.invoiceKind ==='出租车'){
        this.form.invoiceTime = Result.Date;
        this.form.cost = Result.Number5_amount;
        this.form.invoiceNum = Result.Number2_code;
        this.form.invoiceCode = Result.Number1_code;
        let cost = parseFloat(Result.Number5_amount);
        this.form.cost = cost;
        this.form.tax = this.form.cost*0.08;
        let regY=new RegExp('年','g')//g代表全部
        let regM=new RegExp('月','g')//g代表全部
        let regD=new RegExp('日','g')//g代表全部
        let newMsg=JSON.stringify(this.form.invoiceTime).replace(regY,'-');
        let newMsg1=JSON.stringify(newMsg).replace(regM,'-');
        let newMsg2=JSON.stringify(newMsg1).replace(regD,'');
        this.form.invoiceTime = newMsg2.slice(7,17)
      }


    },
  }
}
</script>

<style scoped>
.dialog1{
  position: absolute;
  left: 5%;
  width: 10%;
  top: 20%;
  bottom: 0;
}
.dialog2{
  position: relative;
  left: 18%;
  width: 80%;
  top: 0;
  bottom: 0;
}
.dialog-footer{
  text-align: right;
}
.customerDetailsDialogClass1{
  width: 60%;
}
</style>
