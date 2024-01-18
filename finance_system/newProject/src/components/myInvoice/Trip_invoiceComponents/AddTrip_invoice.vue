<template>
  <div class="editMyinvoice">
    <el-dialog title="新建行程票"
               :visible.sync="NewTripVisible"
               custom-class="DialogClass2"
               :append-to-body="true">
      <div class="dialog1">
        <up-load-form  ref="upload" :imageType="this.form.invoiceKind" @receiveResult="receiveResult"></up-load-form>
      </div>
      <div class="dialog2">
        <el-form :model="form" :inline="true">
          <el-form-item label="出发地" :label-width="formLabelWidth">
            <el-input v-model="form.startPlace" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="目的地" :label-width="formLabelWidth">
            <el-input v-model="form.endPlace" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="出发时间" :label-width="formLabelWidth">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.startTime" style="width: 205px;"></el-date-picker>
          </el-form-item>

          <el-form-item label="到达时间" :label-width="formLabelWidth">
            <el-date-picker type="date" placeholder="选择日期" v-model="form.endTime" style="width: 205px;"></el-date-picker>
          </el-form-item>

          <el-form-item label="票价" :label-width="formLabelWidth">
            <el-input v-model="form.cost" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="车票代码" :label-width="formLabelWidth">
            <el-input v-model="form.tripCode" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="票据类型" :label-width="formLabelWidth">
            <el-select v-model="form.invoiceKind" placeholder="请选择" style="width: 200px">
              <el-option label="汽车票" value="汽车票"></el-option>
              <el-option label="火车票" value="火车票"></el-option>
              <el-option label="飞机票" value="飞机票"></el-option>
              <el-option label="船票" value="船票"></el-option>
            </el-select>


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
  name: "AddTrip_invoice",
  components:{UpLoadForm},
  data(){
    return{
      form: {
        startPlace:'',
        endPlace:'',
        startTime:'',
        endTime:'',
        cost:'',
        tripInvoiceId:'',
        fileId:'',
        invoiceReceiptsId:0,
        tax: '',
        tripCode:''
      },
      textarea: '',
      NewTripVisible:false,
      formLabelWidth: '120px',
      hideUpload: false,
      usrId: '0'
    }
  },
  methods:{
    showAdd(){
      // this.getTax()
      // console.log(this.Tax)
      this.form.cost = null;
      this.form.startPlace = null;
      this.form.endPlace = null;
      this.form.startTime = null;
      this.form.endTime = null;
      this.form.tripInvoiceId = null;
      this.form.tax = null;
      this.form.tripCode = null;
      // this.form.invoiceKind = null;
      // this.$refs.upload.handleRemove()
      this.NewTripVisible = true;

    },
    AddReceipts(ID){
      this.form.invoiceReceiptsId = ID
    },
    NewSuccess(){
      switch (this.form.invoiceKind){
        case "火车票":
          this.form.tax = 0.09;
          break;
        case "汽车票":
          this.form.tax = 0.08;
          break;
        case "飞机票":
          this.form.tax = 0.04;
          break;
        case "船票":
          this.form.tax = 0.05;
          break;
      }
      this.$post({
        url:"/api/tripInvoice/checkTrip",
        data:{
          num: this.form.tripCode
        }
      })
        .then((response)=>{
          if(response.data == "有重复"){
            this.$message({
              type: 'warning',
              message: '有重复票据，添加失败'
            });
            this.NewTripVisible = false;
          }
          if(response.data == "无重复"){
            this.$post({
              url:"/api/tripInvoice/addTripInvoice",
              data:{
                cost: this.form.cost,
                endPlace: this.form.endPlace,
                endTime: this.form.endTime,
                fileId: this.$refs.upload.file_id,
                invoiceReceiptsId:this.form.invoiceReceiptsId,
                startPlace: this.form.startPlace,
                startTime: this.form.startTime,
                taxRate: this.form.tax,
                tax:this.form.tax * this.form.cost,
                tripInvoiceId: 0,
                tripCode:this.form.tripCode,
                usrId:localStorage.getItem('userId')
              }
            })
              .then((response)=>{
                this.$emit('Getdata');
                this.$refs.upload.handleRemove()
                this.NewTripVisible = false;
                this.$message({
                  type: 'success',
                  message: '新建成功!'
                });
              })
          }
        })


    },
    NewCancel(){
      this.NewTripVisible = false;
      this.$message({
        message: '取消新建'
      });
    },
    //下拉框
    getTax(){
      this.$post({
        url:"/api/tax/queryTaxRate",
        data:{
          page:1,
          pageSize:100
        }
      })
        .then((response)=>{
          this.Tax = response.data.records
        })
    },
    receiveResult(Result){
      if(this.form.invoiceKind ==='火车票'){
        let regY=new RegExp('年','g')//g代表全部
        let regM=new RegExp('月','g')//g代表全部
        let regD=new RegExp('日','g')//g代表全部
        let newMsg=JSON.stringify(Result.Date).replace(regY,'-');
        let newMsg1=JSON.stringify(newMsg).replace(regM,'-');
        let newMsg2=JSON.stringify(newMsg1).replace(regD,'');
        this.form.startTime = newMsg2.slice(7,17)
        this.form.endTime = newMsg2.slice(7,17)
        let cost = Result.Number3_Amount.slice(1);
        let cost1 = parseFloat(cost);
        this.form.cost = cost1
        this.form.startPlace = Result.Station_From
        this.form.endPlace = Result.Station_To
        this.form.tripCode = Result.Number5
      }
    },
  }
}
</script>

<style>
.dialog-footer{
  text-align: right;
}
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
.customerDetailsDialogClass2{
  width: 60%;
}
</style>

