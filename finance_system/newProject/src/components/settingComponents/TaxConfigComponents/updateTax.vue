<template>
  <el-dialog title="编辑税务信息" :append-to-body="true" :visible.sync="editDialogFormVisible" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">

    <el-form :model="editTaxData">
      <el-form-item label="税务种类" :label-width="formLabelWidth">
        <el-input v-model="editTaxData.taxType" autocomplete="off"></el-input>
      </el-form-item>


      <el-form-item label="税率" :label-width="formLabelWidth">
        <el-input v-model="editTaxData.taxRate" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="editDialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleEditConfirm">确 定</el-button>
    </div>

  </el-dialog>
</template>

<script>
export default {
  name: "updateTax",
  data(){
    return{
      editTaxData: {taxType:'',taxRate:''},
      editDialogFormVisible:false,
      formLabelWidth: '100px',
      id:0

    }

  },
  methods:{
    handleEditConfirm(){
      this.$post({
          url:'/api/tax/updateTaxRate',
          data:{
            taxRateId:this.id,
            taxRateType:this.editTaxData.taxType,
            taxRate:this.editTaxData.taxRate
          }
        }
      ).then((res)=>{
        this.$emit('getData')
        if(res.data){
          this.$notify({
            title: '成功',
            message: '差旅报销更新成功',
            type: 'success'
          });
        }
        else {
          this.$notify.error({
            title: '错误',
            message: '差旅报销更新失败'
          });
        }
      })
      this.editDialogFormVisible = false
    }
    ,
    showEdit(row){
      this.id=row.taxRateId
      this.editTaxData.taxRate=row.taxRate
      this.editTaxData.taxType=row.taxRateType
      this.editDialogFormVisible=true
    }

  }
}
</script>

<style scoped>

</style>
