<template>
  <div id="updateDestination">
       <el-dialog title="编辑差旅补助" :append-to-body="true" :visible.sync="dialogFormVisible" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="目的地" >
          <el-input v-model="form.city" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="补助费用">
          <el-input v-model="form.cost" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
    data(){
        return{
            dialogFormVisible:false,
            form:{
                subsidyId:0,
                city:'',
                cost:null
            }
        }
    },
    methods:{
        showDialog(row){
            this.form.cost=row.cost
            this.form.city=row.city
            this.dialogFormVisible=true
          this.form.subsidyId=row.subsidyId
        },
        handleEditConfirm(){
            this.$post({
                url:'/api/subsidy/updateSubsidy',
                data:this.form
                }
            ).then((res)=>{
                this.$parent.getData()
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
            this.dialogFormVisible = false
        }
    }
}
</script>

<style>

</style>
