<template>
  <div id="invoice">
    <el-container>
      <el-header >
        <el-row  justify="right" >
          <el-col :span="12" :push="1"><div class="grid-content bg-purple">
            <el-input v-model="search" size="small" style="width: 200px; " placeholder="查询" />
            <el-button size="small" type="primary" @click="handleSearch" >查询</el-button>
            <el-button size="small" @click="handleRemake" >重置</el-button>
          </div></el-col>
          <el-col :span="1" :push="10"><div class="grid-content bg-purple">
            <el-button size="small" type="primary" @click=AddMyinvoice>新建</el-button>
          </div></el-col>
        </el-row>
      </el-header>

      <el-main>
        <el-table :data="tableData" stripe style="width: 100%" height="525">
          <el-table-column label="发票类型" prop="invoiceKind" :width='tableW'></el-table-column>
          <el-table-column label="发票号码" prop="invoiceNum" :width='tableW'></el-table-column>
          <el-table-column label="开票时间" prop="invoiceTime" :width='tableW'>
            <template slot-scope="scope">
              <span>{{ parseDateFull(scope.row.invoiceTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="分类" prop="kind" :width='tableW'></el-table-column>
          <el-table-column label="税价合计" prop="cost" :width='tableW'></el-table-column>
          <el-table-column label="状态" prop="stage" :width='tableW' :formatter="formatterColumn"></el-table-column>
          <el-table-column align="center" label="操作" min-width="180" >
            <template #default="scope">
              <el-button size="small" @click="EditMyinvoice(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          style="left: 50%;bottom:0;height:10%;position: absolute;transform: translateX(-50%);"
          @current-change="handleCurrentChange"
          :current-page='page'
          :page-size='pageSize'
          layout="total, prev, pager, next, jumper"
          :total="this.total">
        </el-pagination>
      </el-main>
  </el-container>
    <EditMy_invoice ref="EditMyinvoice" v-on:Getdata="GetData"></EditMy_invoice>
    <AddMy_invoice ref="AddMyinvoice" v-on:Getdata="GetData"></AddMy_invoice>
  </div>
</template>

<script>
import EditMy_invoice from "./My_invoiceComponents/EditMy_invoice";
import AddMy_invoice from "./My_invoiceComponents/AddMy_invoice";
export default {
  name:"",
  components:{
    EditMy_invoice,AddMy_invoice
  },
    data() {
    return {
      tableW:'180',
      hideUpload: false,
      photo:'',
      input: '',
      search:'',
      NewInvoice: false,
      EditInvoice:false,
      formLabelWidth: '120px',
      textarea: '',
      usrId:'1',
      tableData: [],
      page:1,
      pageSize:7,
      total:0,
      form: {
        invoiceId:'',
        invoiceTime: '',
        kind: '',
        cost: '',
        invoiceKind: '',
        invoiceNum: '',
        invoiceCode: '',
        checkCode: '',
        note:'',
        select:'',
      },
    }
  },
  methods: {
    EditMyinvoice(row){
      if(row.state != '0'&&row.state != null){
        this.$message({
          type: 'warning',
          message: '已提交，不可修改'
        });
      }
      if(row.state == '0'||row.state == null){
        this.$refs.EditMyinvoice.showEdit(row);
      }

    },
    AddMyinvoice(){
      this.$refs.AddMyinvoice.showAdd();
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
            url:"/api/invoice/deleteInvoice",
            data:{
              id: row.invoiceId
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
    //查询
    handleSearch(){
      this.page = 1
      this.$post({
        url:"/api/invoice/queryInvoice",
        data:{
          invoiceNum: this.search,
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
      this.search = ""
      this.GetData()
    },
    //加载表格
    GetData(){
      this.$post({
        url:"/api/invoice/queryInvoice",
        data:{
          // usrID:'0',
          page: this.page,
          pageSize: this.pageSize,
          usrId:localStorage.getItem('userId')
          // invoiceNum: this.search,
        }
      })
      .then((response)=>{
        this.tableData = response.data.records;
        this.total = response.data.total;
      })
    },
    //修改状态信息
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
    //格式化时间
    parseDateFull(time) {
      var x = new Date(time);
      var z = {
        y: x.getFullYear(),
        M: x.getMonth() + 1,
        d: x.getDate(),
      };
      if (z.M < 10) {
        z.M = "0" + z.M;
      }
      if (z.d < 10) {
        z.d = "0" + z.d;
      }
      return z.y + "-" + z.M + "-" + z.d;
    },
    // 切换分页
    handleCurrentChange: function(currentPage){
      this.page = currentPage;//点击第几页
      let s = this.search.length
      if(s != 0){
        this.$post({
          url:"/api/invoice/queryInvoice",
          data:{
            invoiceNum: this.search,
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
      if(s == 0){
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
.DialogClass1{
  width: 60%;
}
</style>
