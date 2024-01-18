<template>
  <div class="editMyinvoice">
    <el-dialog title="新建其他发票"
               :visible.sync="NewInvoice"
               custom-class="DialogClass"
               :append-to-body="true">
      <div class="dialog1">
        <up-load-form  ref="upload"></up-load-form>
      </div>
      <div class="dialog2">
        <el-form :model="form" :inline="true">
          <el-form-item label="合计" :label-width="formLabelWidth">
            <el-input v-model="form.cost" autocomplete="off"></el-input>
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
          <el-form-item label="发票名" :label-width="formLabelWidth">
            <el-input v-model="form.noninvoiceName"></el-input>
          </el-form-item>
          <el-form-item label="发票备注" :label-width="formLabelWidth">
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
  name: "AddNONinvoice",
  components:{UpLoadForm},
  data(){
    return{
      form: {
        createTime: '2020-01-01',
        kind: '',
        cost: '',
        note: '',
        noninvoiceId: 0,
        fileId: '',
        noninvoiceName:'11',
        invoiceReceiptsId:''
      },
      textarea: '',
      NewInvoice:false,
      formLabelWidth: '120px',
      hideUpload: false,
      usrId: '0'
    }
  },
  methods:{
    showAdd(){
      this.form.date = null;
      this.form.kind = null;
      this.form.cost = null;
      this.form.noninvoiceName = null
      this.textarea = null;
      this.files = null;
      this.NewInvoice = true;
    },
    AddReceipts(ID){
      this.form.invoiceReceiptsId = ID
    },
    NewSuccess(){
      this.$post({
        url:"/api/noInvoice/addnoInvoice",
        data:{
          cost: this.form.cost,
          fileId: this.$refs.upload.file_id,
          kind: this.form.kind,
          noninvoiceName: this.form.noninvoiceName,
          invoiceReceiptsId:this.form.invoiceReceiptsId,
          note: this.textarea,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.$emit('Getdata');
          this.$refs.upload.handleRemove()
          this.NewInvoice = false;
          this.$message({
            type: 'success',
            message: '新建成功!'
          });

        })
    },
    NewCancel(){
      this.NewInvoice = false;
      this.$message({
        message: '取消新建'
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
.customerDetailsDialogClass{
  width: 60%;
}
</style>

