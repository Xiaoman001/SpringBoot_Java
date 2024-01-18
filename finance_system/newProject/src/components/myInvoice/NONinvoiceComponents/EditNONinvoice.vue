<template>
  <div class="editMyinvoice">
    <el-dialog title="编辑其他发票"
               :visible.sync="EditInvoice"
               custom-class="DialogClass"
               :append-to-body="true">
      <div class="dialog1">
        <img :src="url" style="height: 150px;width: 150px;left: 0;top: 0"></img>
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
        <el-button @click="EditCancel">取 消</el-button>
        <el-button type="primary" @click="EditSuccess">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import UpLoadForm from '../../Forms/upload.vue';
export default {
  name: "EditNONinvoice",
  components:{UpLoadForm},
  data(){
    return{
      fits: ['fill'],
      url: 'http://121.43.229.173:6790/20220114195711.jpg',
      form: {
        createTime: '2020-01-01',
        kind: '',
        cost: '',
        note: '',
        noninvoiceId: '',
        fileId: '',
        noninvoiceName:'11'
      },
      textarea: '',
      EditInvoice:false,
      formLabelWidth: '120px',
      hideUpload: false,
    }
  },
  methods:{
    showEdit(row) {
      this.form.cost = row.cost;
      this.form.createTime = row.createTime;
      this.form.kind = row.kind;
      this.form.fileId = row.fileId;
      this.form.noninvoiceId = row.noninvoiceId;
      this.textarea = row.note;
      this.form.noninvoiceName = row.noninvoiceName;
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
      this.EditInvoice = true;
    },
    EditSuccess(){
      this.$post({
        url:"/api/noInvoice/updatenoInvoice",
        data:{
          cost: this.form.cost,
          kind: this.form.kind,
          noninvoiceId: this.form.noninvoiceId,
          noninvoiceName: this.form.noninvoiceName,
          note: this.textarea,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.$emit('Getdata');
          this.EditInvoice = false;
          this.$message({
            type: 'success',
            message: '编辑成功!'
          });

        })
    },
    EditCancel(){
      this.EditInvoice = false;
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
