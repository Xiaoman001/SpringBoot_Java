<template>
  <div id="update">
      <el-dialog title="编辑BASE" :visible.sync="dialogFormVisible" :append-to-body="true" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">
            <el-form  label-width="120px">
                <el-form-item label="城市">
                <el-input v-model="form.city" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="详细地址" >
                <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="editConfirm">确 定</el-button>
            </div>
        </el-dialog>
  </div>
</template>

<script>
export default {
    data () {
        return{
            form:{
                city:'',
                baseId:0,
                address:''
            },
            dialogFormVisible:false
        }
    },
    methods :{
        showDialog(row){
            this.form.city=row.city
            this.form.baseId=row.baseId
            this.form.address=row.address
            this.dialogFormVisible=true
        },
        editConfirm(){
            this.$post({
                url:'/api/base/updateBase',
                data:this.form
                }
            ).then((res)=>{
                if(res.data){
                this.$parent.getData()
                this.$notify({
                    title: '成功',
                    message: 'BASE地更新成功',
                    type: 'success'
                });
                }
                else {
                this.$notify.error({
                    title: '错误',
                    message: 'BASE地更新失败'
                });
                }
            })
            this.dialogFormVisible = false
        },
    }
}
</script>

<style>

</style>
