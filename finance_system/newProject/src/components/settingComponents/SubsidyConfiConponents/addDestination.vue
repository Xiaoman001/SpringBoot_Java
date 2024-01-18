<template>
  <div id="addDestination">
      <el-dialog title="新建差旅补助" :append-to-body="true" :visible.sync="dialogFormVisible" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">
      <el-form :model="form" label-width="120px" >
        <el-form-item label="目的地"  >
          <el-input v-model="form.city" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="补助费用"  >
          <el-input v-model="form.cost" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleNew">确 定</el-button>
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
                city:'',
                cost:0
            }
        }
    },
    methods:{
        showDialog(){
            this.dialogFormVisible=true
        },
        handleNew(){
          if (this.form.city===''&&this.form.cost===''){
            this.$message({
              message: '字段不可全部为空',
              type: 'warning'
            });
            this.dialogFormVisible = false
          }
          else {
            this.$post({
                url:'/api/subsidy/addSubsidy',
                data:this.form
              }
            ).then((res)=>{
              this.$parent.getData()
              if(res.data){
                this.$notify({
                  title: '成功',
                  message: '差旅补助更新成功',
                  type: 'success'
                });
              }
              else {
                this.$notify.error({
                  title: '错误',
                  message: '差旅补助更新失败'
                });
              }
            })
            this.dialogFormVisible = false
          }
        },
    }
}
</script>

<style>

</style>
