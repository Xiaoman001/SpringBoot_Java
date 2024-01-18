<template>
  <div id="tripInvoice">
      <el-container >
        <el-header>
          <el-row  justify="right">
            <el-col :span="12" :push="1"><div class="grid-content bg-purple">
              <el-input v-model="search" size="small" style="width: 200px; " placeholder="查询" />
              <el-button size="small" type="primary" @click="handleSearch" >查询</el-button>
              <el-button size="small" @click="handleRemake" >重置</el-button>
            </div></el-col>
            <el-col :span="1" :push="10"><div class="grid-content bg-purple">
              <el-button size="small" type="primary" @click="AddTrip_invoice">新建</el-button>
            </div></el-col>
          </el-row>
        </el-header>
        <el-main>
          <el-table :data="tripList" stripe style="width: 100%;" height="525" >
            <el-table-column label="起始地" prop="startPlace" :width="colWid"/>
            <el-table-column label="目的地" prop="endPlace" :width="colWid"/>
            <el-table-column label="出发时间" prop="startTime" :width="colWid">
              <template slot-scope="scope">
                <span>{{ parseDateFull(scope.row.startTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="到达时间" prop="endTime" :width="colWid">
              <template slot-scope="scope">
                <span>{{ parseDateFull(scope.row.endTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="票价" prop="cost" :width="colWid"/>
            <el-table-column label="状态" prop="state" :width='colWid' :formatter="formatterColumn"></el-table-column>
            <el-table-column align="center" label="操作" min-width="180" >
              <template #default="scope">
                <el-button size="small" @click="EditTrip_invoice(scope.row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row);deleteFromVisible=true">删除</el-button>
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
    <AddTrip_invoice ref="AddTrip_invoice" v-on:Getdata="GetData"></AddTrip_invoice>
    <EditTrip_invoice ref="EditTrip_invoice" v-on:Getdata="GetData"></EditTrip_invoice>
  </div>
</template>

<script>
import EditTrip_invoice from "./Trip_invoiceComponents/EditTrip_invoice";
import AddTrip_invoice from "./Trip_invoiceComponents/AddTrip_invoice";
export default {
  name: "tripInvoice",
  components:{
    EditTrip_invoice,AddTrip_invoice
  },
  data (){
    return{
      colWid:'180',
      tripList:[],
      search:'',
      newDialogFormVisible:false,
      editDialogFormVisible:false,
      deleteFromVisible:false,
      formLabelWidth: '85px',
      hideUpload: false,
      NewTripVisible:false,
      page:1,
      pageSize:7,
      photo:'',
      total:0,
      form: {
        startPlace:'',
        endPlace:'',
        startTime:'',
        endTime:'',
        cost:'',
        tripInvoiceId:''
      },
      textarea:'',
      dialogVisible:false,
      dialogImageUrl:'',



    }
  },
  methods:{
    GetData() {
      this.$post({
        url:"/api/tripInvoice/queryTripInvoice",
        data:{
          // usrID:'0',
          page: this.page,
          pageSize: this.pageSize,
          usrId:localStorage.getItem('userId')
          // address: this.search,
        }
      })
        .then((response)=>{
          this.tripList = response.data.records;
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
    AddTrip_invoice(){
      this.$refs.AddTrip_invoice.showAdd();
      // this.$refs.AddTrip_invoice.getTax();
    },
    EditTrip_invoice(row){
      if(row.state != '0'&&row.state != null){
        this.$message({
          type: 'warning',
          message: '已提交，不可修改'
        });
      }
      if(row.state == '0'||row.state == null){
        this.$refs.EditTrip_invoice.showEdit(row);
      }
    },
    //删除
    handleDelete(index,row){
      if(row.state != '0'&&row.state != null){
        this.$message({
          type: 'warning',
          message: '已提交，不可删除'
        });
      }
      if(row.state == '0'||row.state == null){
        this.$confirm('此操作将永久删除该车票, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$post({
            url:"/api/tripInvoice/deleteTripInvoice",
            data:{
              id: row.tripInvoiceId
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
        url:"/api/tripInvoice/queryTripInvoice",
        data:{
          address: this.search,
          page:this.page,
          pageSize:this.pageSize,
          usrId:localStorage.getItem('userId')
        }
      })
        .then((response)=>{
          this.tripList = response.data.records;
          this.total = response.data.total;
        })
    },
    //重置
    handleRemake(){
      this.search = ''
      this.GetData()
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
      if(Object.keys(this.search).length != 0){
        this.$post({
          url:"/api/tripInvoice/queryTripInvoice",
          data:{
            address: this.search,
            page:this.page,
            pageSize:this.pageSize,
            usrId:localStorage.getItem('userId')
          }
        })
          .then((response)=>{
            this.tripList = response.data.records;
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
.DialogClass2{
  width: 60%;
}
</style>
