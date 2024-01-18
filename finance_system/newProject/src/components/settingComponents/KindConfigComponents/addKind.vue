<template>
  <div id="addKind">
      <el-dialog title="新建报销种类" :append-to-body="true" :visible.sync="dialogFormVisible" style="position: absolute;left:20%;top: 0; width: 850px;height: 500px">

      <el-form v-model="form" label-width="120px">
        <el-form-item label="报销种类" >
          <el-input v-model="form.kind" autocomplete="off"></el-input>
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
                kind:''
            }
        }
    },
    methods:{
        showDialog(){
            this.dialogFormVisible=true;
        },
        handleNew(){
        if (this.form.kind===''){
          this.$message({
            message: '字段不可全部为空',
            type: 'warning'
          });
          this.dialogFormVisible = false
        }
        else {
          this.$post({
              url:'/api/kind/addKind',
              data:{
                kind:this.form.kind
              }
            }
          ).then((res)=>{
            this.$parent.getData()
            this.dialogFormVisible = false
          })
        }




        },
    }

}
</script>

<style>

</style>
