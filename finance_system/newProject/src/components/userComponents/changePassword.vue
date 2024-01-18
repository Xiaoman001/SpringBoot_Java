<template>
  <div id="changePassword">
      <el-dialog title="修改密码" :visible.sync="dialogFormVisible" :append-to-body="true" >
        <el-form :model="form" :rules="rules" status-icon ref="ruleForm">
            <el-form-item label="原密码" label-width='120px' prop="originPassword">
                <el-input v-model="form.originPassword" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label="新密码" label-width="120px" prop="newPassword">
                <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" label-width="120px" prop="confirmPassword">
                <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
            </el-form-item>
        </el-form>


        <div slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" v-on:click="updatePasswd">确 定</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
    data () {
        let validateOriginPassword = (rule, value, callback) => {
          if (value !== this.form.originPassword) {
            callback(new Error('请输入正确的原密码!'))
          } else {
            callback()
          }
        }
        let validateNewPassword = (rule, value, callback) => {
          if (value === this.form.originPassword) {
            callback(new Error('新密码不能与原密码相同!'))
          } else {
            callback()
          }
        }
        let validateRepeatNewPassword = (rule, value, callback) => {
          if (value !== this.form.newPassword) {
            callback(new Error('两次输入的密码不一致!'))
          } else {
            callback()
          }
        }

        return{
            dialogFormVisible:false,
            form:{
                originPassword:'',
                newPassword:'',
                confirmPassword:''
            },
          rules: {
            originPassword: [
              { required: true, message: '请输入原密码', trigger: 'blur' },
              { validator: validateOriginPassword, trigger: 'blur' }
            ],
            newPassword: [
              { required: true, message: '请设置新密码', trigger: 'blur' },
              { validator: validateNewPassword, trigger: 'blur' }
            ],
            confirmPassword: [
              { required: true, message: '请确认新密码', trigger: 'blur' },
              { validator: validateRepeatNewPassword, trigger: 'blur' }
            ]
          }
        }
    },
    methods :{
        showDialog() {
            this.dialogFormVisible=true
        },
        cancel(){
          this.$refs.ruleForm.resetFields()
          this.dialogFormVisible = false
        },
        updatePasswd(){
            this.$post({
                url: '/api/changePasswd',
                data: {
                    newPasswd: this.form.newPassword,
                    originPasswd: this.form.originPassword,
                    userName: localStorage.getItem('username')
                }
            }).then(response => {
              if(response.code === 201)
                this.$message.success('密码已修改');
              else
                this.$message.error('修改失败！');
            })

            this.$refs.ruleForm.resetFields()
            this.dialogFormVisible=false
        }
    }
}
</script>

<style>

</style>