<template>
  <div id="messages" style="width:100%;margin-top:10px">
    我的信箱
    <el-divider></el-divider>
    <el-table
    :data="messageTable"
    style="width: 100%">
    <el-table-column
      prop="time"
      label="日期"
      width="180"
      :formatter=" dateFormatter"
      align="center">
    </el-table-column>
    <el-table-column
      prop="noticeStste"
      label="状态"
      width="180"
      align="center">
      <template slot-scope="scope">
        <el-tag :type="scope.row.noticeStste=='1' ?'success':'warning'">
          {{ scope.row.noticeStste== '1' ? '已读' :  '未读' }}
        </el-tag>
        </template>
    </el-table-column>
    <el-table-column
      prop="note"
      label="留言"
      min-width="180"
      align="center"
      >
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="200"
      align="center">
      <template slot-scope="scope">
        <el-button @click="confirmMessage(scope.row)"  size="small" v-show="scope.row.noticeStste=='0'">已读</el-button>
        <el-button type="danger" size="small" @click="deleteMessage(scope.row)" v-show="scope.row.noticeStste!='0'">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
   <el-pagination
          style="width: 200px; right: 50%;bottom:0;height:10%;position: absolute"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-size="pageSize"
          :hide-on-single-page="true"
          layout="total, prev, pager, next, jumper"
          :total="this.total">
    </el-pagination>
  </div>
</template>

<script>
export default {
    data() {
      return {
        messageTable:[],
        page:1,
        pageSize:7,
        total:0,
      }
    },
    methods: {
      initMessage(){
        this.$post({
                url: '/api/notice/queryNotice',
                data: {
                  page: this.page,
                  pageSize: this.pageSize,
                  userId: localStorage.getItem('userId')
                }
            }).then(response => {
              if(response.code=='200'){
                this.messageTable=response.data.records
                this.total=response.data.total
            }
        })
      },
      // handleCurrentChange(){
      //   this.initMessage();
      // },
      dateFormatter (row, column) {
        let datetime = row.time;
        if(datetime){
          datetime = new Date(datetime);
          let y = datetime.getFullYear() + '-';
          let mon = datetime.getMonth()+1 + '-';
          let d = datetime.getDate();
          let h = datetime.getHours() + ':';
          let m = datetime.getMinutes() + ':';
          let s = datetime.getSeconds() ;
          return y + mon + d +" " +h + m +s;
        }
        return ''
      },
      confirmMessage(row){
        this.$post({
                url: '/api/notice/updateNotice',
                data: {
                  noticeId: row.noticeId,
                  noticeStste: "1"
                }
            }).then(response => {
              if(response.code=='201'){
                this.initMessage()
            }
        })
      },
      deleteMessage(row){
        this.$post({
                url: '/api/notice/deleteNotice',
                data: {
                  id: row.noticeId
                }
            }).then(response => {
              if(response.code=='201'){
                this.initMessage()
                this.$notify({
                  title: '成功',
                  message: '删除成功',
                  type: 'success'
                });
            }
        })
      },
      handleCurrentChange(currentPage){
          this.page=currentPage
          this.initMessage()
      }
    },

    mounted(){
      this.initMessage()
    }

}
</script>

<style>
#message{
  width: 100%;
}
.el-divider--horizontal{
  margin: 10px 0;
  display: block;
  height: 1px;
  width: 100%;
  margin: 10px 0;
}

</style>
