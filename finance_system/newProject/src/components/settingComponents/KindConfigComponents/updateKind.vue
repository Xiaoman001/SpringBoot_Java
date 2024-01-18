<template>
  <div id="updateKind">
    <el-dialog title="编辑报销种类" :append-to-body="true" :visible.sync="dialogFormVisible" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">

      <el-form :model="form" label-width="120px">
        <el-form-item label="报销种类">
          <el-input v-model="form.kind" autocomplete="off"></el-input>
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
    data(){
        return{
            dialogFormVisible:false,
            form:{
                kindId:'',
                kind:''
            }
        }
    },
    methods:{
        showDialog(row){
            this.form.kind=row.kind
            this.form.kindId=row.kindId
            this.dialogFormVisible=true
        },
        editConfirm(){
            this.$post({
                url:'/api/kind/updateKind',
                data:this.form
                }
            ).then((res)=>{
                this.$parent.getData()
                if(res.data){
                this.$notify({
                    title: '成功',
                    message: '报销种类更新成功',
                    type: 'success'
                });
                }
                else {
                this.$notify.error({
                    title: '错误',
                    message: '报销种类更新失败'
                });
                }
            })
            this.dialogFormVisible = false;
        }

    }

}
</script>

<style>

</style>
