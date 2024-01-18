<template>
  <div class="editMyinvoice">
    <el-dialog title="编辑行程票"
               :visible.sync="editDialogFormVisible"
               custom-class="DialogClass2"
               :append-to-body="true">
      <div class="dialog1">
        <img :src="url" style="height: 150px;width: 150px;left: 0;top: 0">
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

        <el-form-item label="税金" :label-width="formLabelWidth">
          <el-input v-model="form.tax" autocomplete="off"></el-input>
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
  name: "EditTrip_invoice",
  components:{UpLoadForm},
  data(){
    return{
      fits: ['fill'],
      url: 'http://121.43.229.173:6790/20220114195711.jpg',
      form: {
        startPlace:'',
        endPlace:'',
        startTime:'',
        endTime:'',
        cost:'',
        tripInvoiceId:'',
        tax: '',
      },
      textarea: '',
      editDialogFormVisible:false,
      formLabelWidth: '120px',
      hideUpload: false,
    }
  },
  methods:{
    showEdit(row) {
      this.form.cost = row.cost;
      this.form.startPlace = row.startPlace;
      this.form.endPlace = row.endPlace;
      this.form.startTime = row.startTime;
      this.form.endTime = row.endTime;
      this.form.tripInvoiceId = row.tripInvoiceId;
      this.form.tax = row.tax;
      this.$post({
        url:"/api/file/downLoadFile",
        data:{id: row.fileId}
      })
        .then((response)=>{
          this.url = 'http://121.43.229.173:6790/20220114195711.jpg'
          if(response){
            let str = 'http://'
            str = str.concat(response)
            this.url = str
          }
        })
      this.editDialogFormVisible = true;
    },
    EditSuccess(){
      this.$post({
        url:"/api/tripInvoice/updateTripInvoice",
        data:{
          tripInvoiceId:this.form.tripInvoiceId ,
          cost: this.form.cost,
          endPlace: this.form.endPlace,
          endTime: this.form.endTime,
          startPlace: this.form.startPlace,
          startTime: this.form.startTime,
          tax : this.form.tax,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.$emit('Getdata');
          this.editDialogFormVisible = false;
          this.$message({
            type: 'success',
            message: '编辑成功!'
          });

        })
    },
    EditCancel(){
      this.editDialogFormVisible = false;
      this.$message({
        message: '取消编辑'
      });
    },
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
