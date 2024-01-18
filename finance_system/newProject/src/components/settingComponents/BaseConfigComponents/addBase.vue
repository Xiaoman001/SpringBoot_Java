<template>
  <div id="addBase">
      <el-dialog title="新建BASE" :visible.sync="dialogFormVisible" :append-to-body="true" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">
      <el-form  label-width="120px" :model="form">
        <el-form-item label="城市"  >
          <el-input v-model="form.city" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="详细地址">
            <el-input v-model="form.address" autocomplete="off"></el-input>
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
    name:'addBase',
    data () {
        return{
            form:{
                city:'',
                address:''
            },
            dialogFormVisible:false
        }
    },
    methods :{
        handleNew(){
          if(this.form.address===''&&this.form.city===''){
            this.$message({
              message: '字段不可全部为空',
              type: 'warning'
            });
            this.dialogFormVisible = false
          }
          else {
            this.$post({
                url:'/api/base/addBase',
                data:{
                  address:this.form.address,
                  baseId:0,
                  city:this.form.city
                }
              }
            ).then((res)=>{
              this.$parent.getData()
              if(res.data){
                this.$notify({
                  title: '成功',
                  message: 'BASE地新增成功',
                  type: 'success'
                });
              }
              else {
                this.$notify.error({
                  title: '错误',
                  message: 'BASE地新增失败'
                });
              }
            })
            this.dialogFormVisible=false
          }



        },
        showDialog(){
            this.dialogFormVisible=true
        }
    }
}
</script>

<style>

</style>
