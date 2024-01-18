<template>
  <div id="Info">
    <el-dialog title="个人信息" :visible.sync="dialogFormVisible" :append-to-body="true">
        <el-descriptions :column="4" border>
            <el-descriptions-item label="用户名" :span="2">
                {{infoForm.username}}
            </el-descriptions-item>
            <el-descriptions-item label="姓名" :span="1">{{infoForm.name}}</el-descriptions-item>
             <el-descriptions-item label="职位" :span="1">
            <el-tag size="small">{{infoForm.position}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="部门" :span="2">{{infoForm.department}}</el-descriptions-item>
            <el-descriptions-item label="BASE地" :span="2">{{infoForm.base}}</el-descriptions-item>
            <el-descriptions-item label="上次差旅补助报销时间" :span="2">{{infoForm.lastTime}}</el-descriptions-item>
            <el-descriptions-item label="当前差旅补助报销时间" :span="2">{{infoForm.applyTime}}</el-descriptions-item>
        </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
export default {
    props:['infoForm'],
    data(){
        return{
            dialogFormVisible:false,
        }
    },
    methods:{
        showDialog() {
            this.dialogFormVisible=true
        }     
    }
}
</script>

<style>

</style>