<template>
  <el-dialog title="新建税务信息" :append-to-body="true" :visible.sync="newDialogFormVisible" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">

    <el-form :model="newTaxData" >
      <el-form-item label="税务种类" :label-width="formLabelWidth" >
        <el-input v-model="newTaxData.taxRateType" autocomplete="off"></el-input>
      </el-form-item>


      <el-form-item label="税率" :label-width="formLabelWidth" >
        <el-input v-model="newTaxData.taxRate" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="newDialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleNew">确 定</el-button>
    </div>

  </el-dialog>
</template>

<script>
export default {
  name: "addTax",
  data(){
    return{
      newTaxData: {taxRateType:'',taxRate:''},
      newDialogFormVisible:false,
      formLabelWidth: '100px',
    }
  },
  methods:{
    showAdd(){
      this.newDialogFormVisible=true
    }
    ,
    handleNew(){
      if (this.newTaxData.taxRateType===''){
        this.$message({
          message: '字段不可全部为空',
          type: 'warning'
        });
        this.dialogFormVisible = false
      }
      else {
        this.$post({
            url:'/api/tax/addTaxRate',
            data:{
              taxRateType:this.newTaxData.taxRateType,
              taxRate:this.newTaxData.taxRate
            }
          }
        ).then((res)=>{
          this.$emit('getData')
          if(res.data){
            this.$notify({
              title: '成功',
              message: '税务信息更新成功',
              type: 'success'
            });
          }
          else {
            this.$notify.error({
              title: '错误',
              message: '税务信息更新失败'
            });
          }
        })
        this.newDialogFormVisible = false
      }


    },
  }

}
</script>

<style scoped>

</style>
