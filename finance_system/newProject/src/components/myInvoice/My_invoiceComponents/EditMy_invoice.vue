<template>
<div class="editMyinvoice">
  <el-dialog title="编辑发票" left :visible.sync="edit_MyinvoiceVisible" custom-class="DialogClass1" :append-to-body="true">
    <div class="dialog1">
      <img :src="url" style="height: 150px;width: 150px;left: 0;top: 0"></img>
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
          <el-select v-model="form.kind" style="width: 200px" placeholder="请选择分类">
            <el-option label="差旅" value="差旅"></el-option>
            <el-option label="办公" value="办公"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发票代码" :label-width="formLabelWidth">
          <el-input v-model="form.invoiceCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="发票类型" :label-width="formLabelWidth">
          <el-select v-model="form.invoiceKind" style="width: 200px" placeholder="请选择发票类型">
            <el-option label="增值税发票" value="增值税发票"></el-option>
            <el-option label="手撕票" value="手撕票"></el-option>
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
          <el-input type="textarea" style="width: 200px" :rows="2" placeholder="请输入内容" v-model="textarea">
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="EditCancel">取 消</el-button>
      <el-button type="primary" @click="EditSuccess">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import UpLoadForm from '../../Forms/upload.vue';
export default {
  name: "EditMy_invoice",
  components:{UpLoadForm},
  data(){
    return{
      fits: ['fill'],
      url: 'http://121.43.229.173:6790/20220114195711.jpg',
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
        tax:'',
        soldTaxid:''
      },
      textarea: '',
      edit_MyinvoiceVisible:false,
      formLabelWidth: '120px',
      hideUpload: false,
    }
  },
  methods:{
    showEdit(row) {
      this.form.cost = row.cost;
      this.form.invoiceTime = row.invoiceTime;
      this.form.kind = row.kind;
      this.form.invoiceKind = row.invoiceKind;
      this.form.invoiceNum = row.invoiceNum;
      this.form.invoiceCode = row.invoiceCode;
      this.form.checkCode = row.checkCode;
      this.textarea = row.note;
      this.form.invoiceId = row.invoiceId;
      this.form.tax = row.tax;
      this.form.soldTaxid = row.soldTaxid;
      this.form.fileId = row.fileId;
      this.$post({
        url:"/api/file/downLoadFile",
        data: {id: row.fileId}
      })
        .then((response)=>{
          this.url = 'http://121.43.229.173:6790/20220114195711.jpg'
          if(response){
            let str = 'http://'
            str = str.concat(response)
            this.url = str
          }
        })
      this.edit_MyinvoiceVisible = true;
    },
    EditSuccess(){
      this.$post({
        url:"/api/invoice/updateInvoice",
        data:{
          invoiceId:this.form.invoiceId,
          checkCode: this.form.checkCode,
          cost: this.form.cost,
          invoiceCode: this.form.invoiceCode,
          invoiceKind: this.form.invoiceKind,
          invoiceName: "string",
          invoiceNum: this.form.invoiceNum,
          invoiceTime: this.form.invoiceTime,
          kind: this.form.kind,
          note: this.textarea,
          soldTaxid: this.form.soldTaxid,
          tax: this.form.tax,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.$emit('Getdata');
          this.edit_MyinvoiceVisible = false;
          this.$message({
            type: 'success',
            message: '编辑成功!'
          });

        })
    },
    EditCancel(){
      this.edit_MyinvoiceVisible = false;
      this.$message({
        message: '取消编辑'
      });
    },
    receiveResult(Result){
      this.form.invoiceTime = Result.vat_invoice_issue_date;
      this.form.cost = Result.unit_price;
      this.form.invoiceNum = Result.vat_invoice_haoma_right_side;
      this.form.invoiceCode = Result.vat_invoice_daima_right_side;
      this.form.checkCode = Result.check_code;
      this.form.tax = Result.vat_invoice_tax_total;
      this.form.soldTaxid = Result.vat_invoice_rate_payer_id;
      let regY=new RegExp('年','g')//g代表全部
      let regM=new RegExp('月','g')//g代表全部
      let regD=new RegExp('日','g')//g代表全部
      let newMsg=JSON.stringify(this.form.invoiceTime).replace(regY,'-');
      let newMsg1=JSON.stringify(newMsg).replace(regM,'-');
      let newMsg2=JSON.stringify(newMsg1).replace(regD,'');
      this.form.invoiceTime = newMsg2.slice(7,17)
    }
  }
}
</script>

<style scoped>
.dialog1{
  position: absolute;
  left: 5%;
  width: 10%;
  top: 30%;
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
</style>
