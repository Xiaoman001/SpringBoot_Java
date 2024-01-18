<template>
  <div id="index">
    <el-container>
        <el-aside :width="isCollapse?'64px':'150px'">
            <el-menu :default-active="currentPage" class="el-menu-vertical-demo" :collapse="isCollapse" :collapse-transition="false">
                <el-menu-item index="1" style="margin-top:50px" @click="changeContent('check')">
                    <i class="el-icon-menu"></i>
                    <span slot="title">个人视图</span>
                </el-menu-item>
                <el-menu-item index="2" @click="changeContent('invoice')">
                    <i class="el-icon-document"></i>
                    <span slot="title">发票管理</span>
                </el-menu-item>
                <el-menu-item index="3" @click="changeContent('pending')" v-show="userGrand=='1'||userGrand=='3'">
                    <i class="el-icon-s-check"></i>
                    <span slot="title">报销审批</span>
                </el-menu-item>
                <el-menu-item index="4" @click="changeContent('setting')" v-show="userGrand=='2'||userGrand=='3'">
                    <i class="el-icon-setting"></i>
                    <span slot="title">配置管理</span>
                </el-menu-item>
                <el-menu-item index="5" @click="changeContent('queryInvoice')" v-show="form.department=='财务'">
                    <i class="el-icon-search"></i>
                    <span slot="title">问题票据</span>
                </el-menu-item>
                <el-menu-item index="6" @click="changeContent('developer')">
                    <i class="el-icon-s-custom"></i>
                    <span slot="title">开发者信息</span>
                </el-menu-item>

                <el-menu-item index="7" @click="changeContent('comment')">
                  <i class="el-icon-s-comment"></i>
                  <span slot="title">留言板</span>
                </el-menu-item>


            </el-menu>
        </el-aside>
        <el-container>
            <el-header height="50px">
                <span class="menuControl el-icon-s-unfold" v-on:click="isCollapse=!isCollapse" v-show="isCollapse"></span>
                <span class="menuControl el-icon-s-fold" v-on:click="isCollapse=!isCollapse" v-show="!isCollapse"></span>
                <span class="userInfo"  style="float:right;margin-right:35px;">
                    <el-tooltip class="item" effect="dark" content="消息通知" placement="bottom"  >
                        <el-badge is-dot class="item" :hidden="messageNumber===0">
                        <span class="el-icon-message" @click="changeContent('message')"></span>
                        </el-badge>
                    </el-tooltip>
                    <span class="user">
                        <el-dropdown @command="handleCommand">
                            <span class="el-dropdown-link">
                                <span class="name" style="float:right" >{{name}}</span>
                                <span class="block" style="float:right"><el-avatar  size="small"  >{{name[0]}}</el-avatar></span>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command="a"  >修改密码</el-dropdown-item>
                                <el-dropdown-item command="b">个人信息</el-dropdown-item>
                                <el-dropdown-item command="c" divided>退出登陆</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </span>
                </span>
            </el-header>
            <el-main class="el-main" >
                <div class="el-main content" >
                    <router-view></router-view>
                </div>
            </el-main>
        </el-container>
        <change-password ref="changePW"></change-password>
        <personal-info ref="showPersonInfo" :infoForm="form"></personal-info>
    </el-container>
  </div>
</template>

<script>
import changePassword from './userComponents/changePassword.vue'
import personalInfo from './userComponents/personalInfo.vue'
export default {
    name: 'Index',
    components:{changePassword,personalInfo},
    data(){
        return{
            isCollapse: true,
            name:'',
            username:'',
            currentPage:"1",
            userGrand:'',
            form:{
                username:'',
                name:'',
                department:'',
                position:'',
                lastTime:'',
                address:'',
                applyTime:'',
                base:'',
                userId:null,
            },
            messageNumber:0
        }
    },
    mounted(){
        this.initUser()
    },
    methods:{
        changeContent(position){
            if(position=='message'){
                this.queryMessage()
            }
            this.$router.push({name:position})
            if(position=='check'){
                this.$store.commit("isNotShow", true);
            }
        },
        handleCommand(command){
            if(command=='a'){
                this.$refs.changePW.showDialog()
            }
            else if(command=='b'){
                this.$refs.showPersonInfo.showDialog()
            }
            else if(command=='c'){
                this.$router.push('/')
            }
        },
        initUser(){
            // 初始化个人信箱
            this.$post({
                url: '/api/queryUser',
                data: {
                    userName:localStorage.getItem('username')
                }
            }).then(response => {
               if(response.data){
                this.name=response.data[0].name
                this.userGrand=response.data[0].grant
                this.form.username=response.data[0].userName
                this.form.name=response.data[0].name
                this.form.department=response.data[0].department
                this.form.lastTime=this.dateFormatter(response.data[0].lastSettleTime)
                this.form.applyTime=this.dateFormatter(response.data[0].applyTime)
                this.form.position=response.data[0].role
                this.form.userId=response.data[0].usrId
                var base_id=response.data[0].baseId
                localStorage.setItem('userId',this.form.userId)
                this.$post({
                    url: '/api/base/queryBase',
                    data: {
                       baseId:base_id,
                       page:1,
                       pageSize:7
                    }
                    }).then(response => {
                    if(response.data){
                        if(response.data){
                            this.form.base=response.data.records[0].address
                        }
                    }
                })
                this.queryMessage()

            }
            })
        },
        dateFormatter (time) {
            let datetime = time;
            if(datetime){
            datetime = new Date(datetime);
            let y = datetime.getFullYear() + '-';
            let mon = datetime.getMonth()+1 + '-';
            let d = datetime.getDate();
            return y + mon + d
            }
            return ''
        },
        queryMessage(){
        // 获取未读消息数
            this.$post({
                url: '/api/notice/queryNoticeNum?userId='+this.form.userId,
            }).then(response=>{
                this.messageNumber=response.data
            })
        }
    }
}
</script>

<style>
#index{
    position: absolute;
    width: 100%;
    height: 100%;
}
.el-container{
    height: 100%;
}
.el-header {
    position: relative;
    background-color: #fff;
    color: #333;
    line-height: 50px;
    padding: 0;
}
.user, .message{
    cursor: pointer;
}
.block , .name{
    position: relative;
    width: 50px;
    height: 50px;
    /* line-height: 50px; */
    text-align: center     ;
}
.message{
    position: relative;
    font-size: 25px;
    top:-20px;
    float: left;
    text-align: right;
    width: 30px;
    height: 30px;
}
.userInfo .el-icon-message{
    font-size: 25px;
    cursor: pointer;
}
.block .el-avatar{
    position: absolute;
    left: 50%;
    top:50%;
    transform: translate(-50%,-50%);
}
.el-aside {
    background-color: #fff;
    color: #333;
    transition: width 0.15s;
    overflow: hidden;
    box-shadow:0 2px 27px 6px rgba(0, 0, 0, .12);
    z-index: 2;
}
.el-menu{
    border: none;
}

.el-main {
    position: relative;
    background-color: #E9EEF3;
    color: #333;
    padding: 0;
}
.el-main .content{
    position: absolute;
    display: flex;
    top:5%;
    right:35px;
    width: calc(100% - 75px);
    min-height:625px ;
    background-color: #fff;
}
.menuControl{
    width: 50px;
    height: 50px;
    line-height: 50px;
    cursor: pointer;
    font-size: 25px;
    text-align: center;
    color: #909399;
}
.menuControl:hover{
    background-color: rgba(64,158,255,0.15);
}
.el-menu-vertical-demo{
    height: 100%;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 150px;
}
.el-badge__content.is-fixed.is-dot {
    position: relative;
    right: 18px;
}
.el-badge {
    position: relative;
    vertical-align: middle;
    display: inline-block;
    top: -15px;
}


</style>
