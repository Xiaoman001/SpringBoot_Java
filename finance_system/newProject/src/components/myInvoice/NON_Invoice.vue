<template>
  <div id="noInvoice">
    <el-container>
      <el-header>
        <el-row justify="right">
          <el-col :span="12" :push="1"><div class="grid-content bg-purple">
            <el-input v-model="search" size="small" style="width: 200px; " placeholder="查询" />
            <el-button size="small" type="primary" @click="handleSearch" >查询</el-button>
            <el-button size="small" @click="handleRemake" >重置</el-button>
          </div></el-col>
          <el-col :span="1" :push="10"><div class="grid-content bg-purple">
            <el-button size="small" type="primary" @click=AddNONinvoice>新建</el-button>
          </div></el-col>
        </el-row>
      </el-header>
      <el-main>
        <el-table :data="tableData" stripe style="width: 100%;" height="525">
          <el-table-column label="分类" prop="kind" :width='tableW'></el-table-column>
          <el-table-column label="合计" prop="cost" :width='tableW'></el-table-column>
          <el-table-column label="发票名" prop="noninvoiceName" :width='tableW'></el-table-column>
          <el-table-column label="状态" prop="state" :width='tableW' :formatter="formatterColumn"></el-table-column>
          <el-table-column align="center" label="操作" min-width="180" >
            <template #default="scope">
              <el-button size="small" @click="EditNONinvoice(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          style=" left: 50%;bottom:0;height:10%;position: absolute;transform: translateX(-50%);"
          @current-change="handleCurrentChange"
          :current-page='page'
          :page-size='pageSize'
          layout="total, prev, pager, next, jumper"
          :total="this.total">
        </el-pagination>
      </el-main>
    </el-container>
    <EditNONinvoice ref="EditNONinvoice" v-on:Getdata="GetData"></EditNONinvoice>
    <AddNONinvoice ref="AddNONinvoice" v-on:Getdata="GetData"></AddNONinvoice>
  </div>
</template>

<script>
import EditNONinvoice from "./NONinvoiceComponents/EditNONinvoice";
import AddNONinvoice from "./NONinvoiceComponents/AddNONinvoice";
export default {
  name:"",
  components:{
    EditNONinvoice,AddNONinvoice
  },
  data() {

    return {
      usrId: '',
      input: '',
      search: '',
      tableW:'260',
      tableData: [{
        createTime: '2020-01-01',
        kind: '差旅',
        cost: '500',
        noninvoiceName: 'test',
        state: '未提交',
        noninvoiceId: '',
        fileId: '',
        note:''
      }],
      NewInvoice: false,
      EditInvoice:false,
      formLabelWidth: '120px',
      textarea: '',
      page:1,
      pageSize:7,
      total:0,
      form: {
        createTime: '2020-01-01',
        kind: '',
        cost: '',
        note: '',
        noninvoiceId: '',
        fileId: '',
        noninvoiceName:'11'
      },
    }
  },
  methods: {
    //编辑
    EditNONinvoice(row){
      if(row.state != '0'&&row.state != null){
        this.$message({
          type: 'warning',
          message: '已提交，不可修改'
        });
      }
      if(row.state == '0'||row.state == null){
        this.$refs.EditNONinvoice.showEdit(row);
      }

    },
    //新建
    AddNONinvoice(){
      this.$refs.AddNONinvoice.showAdd();
    },
    //删除
    handleDelete(index, row) {
      if(row.state != '0'&&row.state != null){
        this.$message({
          type: 'warning',
          message: '已提交，不可删除'
        });
      }
      if(row.state == '0'||row.state == null){
        this.$confirm('此操作将永久删除该发票, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$post({
            url:"/api/noInvoice/deletenoInvoice",
            data:{
              id: row.noninvoiceId
            }
          })
            .then((response)=>{
              this.GetData()
              this.$message({
                type: 'warning',
                message: '删除成功'
              });
            })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }

    },
    //搜索
    handleSearch(){
      this.page = 1
      this.$post({
        url:"/api/noInvoice/queryNoInvoice",
        data:{
          noninvoiceName: this.search,
          page:this.page,
          pageSize:this.pageSize,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.tableData = response.data.records;
          this.total = response.data.total;
        })

    },
    //重置
    handleRemake(){
      this.search = ''
      this.GetData()

    },
    //获取data
    GetData(){
      this.$post({
        url:"/api/noInvoice/queryNoInvoice",
        data:{
          // usrID:'0',
          page: this.page,
          pageSize: this.pageSize,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.tableData = response.data.records;
          this.total = response.data.total;
        })
    },
    //状态
    formatterColumn(row,column){
      if(row.state == null){
        return '未发起'
      }
      switch(row.state){
        case '0':
          return '未发起';
          break;
        case '1':
          return '部门经理审批';
          break;
        case '2':
          return '财务部门审批';
          break;
        case '3':
          return '总经理审批';
          break;
        case '4':
          return '审批完成';
          break;
        case '5':
          return '异常';
          break;
      }
    },
    // 切换分页
    handleCurrentChange: function(currentPage){
      this.page = currentPage;//点击第几页
      if(Object.keys(this.search).length != 0){
        this.$post({
          url:"/api/noInvoice/queryNoInvoice",
          data:{
            noninvoiceName: this.search,
            page:this.page,
            pageSize:this.pageSize,
            usrId:localStorage.getItem('userId')
          }
        })
          .then((response)=>{
            this.tableData = response.data.records;
            this.total = response.data.total;
          })
      }
      if(Object.keys(this.search).length == 0){
        this.GetData()
      }
    },
  },
  mounted() {
    this.GetData();
  }
}
</script>

<style>
.DialogClass{
  width: 60%;
}
</style>
