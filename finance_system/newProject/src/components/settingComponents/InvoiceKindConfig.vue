<template>
<div>
    <el-header align="right" >
      <el-input  v-model="search" size="small" style="width: 200px; right: 15px" placeholder="查询"  />
      <el-button size="small" type="primary" @click="handleSearch" style="position:relative;">查询</el-button>
      <el-button size="small" @click="handleRemake" style="position:relative;">重置</el-button>
      <el-button size="small" type="primary" @click="showAddKind" style="position: relative;right: 10px">新建</el-button>
    </el-header>
    <el-main>
      <el-table :data="showKindList" height="525" >
        <el-table-column label="报销ID" prop="kindId" />
        <el-table-column label="报销种类" prop="kind" />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button size="small" @click="showUpdateKind(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="width: 200px; right: 50%;bottom:0;height:10%;position: absolute"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="this.total">
      </el-pagination>
    </el-main>
    <add-kind ref="addKind"></add-kind>
    <update-kind ref="updateKind"></update-kind>
</div>
</template>

<script>
import addKind from './KindConfigComponents/addKind.vue'
import updateKind from './KindConfigComponents/updateKind.vue'
export default {
  name: "InvoiceKindConfig",
  components:{
    addKind,updateKind
  },
  data (){
    return{
      kindId:0,
      kindList:[],
      showKindList:[],
      search:'',
      page:1,
      pageSize:7,
      pages:2,
      total:0,
    }
  },
  mounted() {
    this.getData()
  }
  ,
  methods:{
    handleCurrentChange(currentPage){
      this.page = currentPage;//点击第几页
      this.getData()
    }
    ,
    showAddKind(){
      this.$refs.addKind.showDialog()
    },
    showUpdateKind(row){
      this.$refs.updateKind.showDialog(row)
    },
    getData(){
      this.$post({
          url:'/api/kind/queryKind',
          data:{
            page:this.page,
            pageSize:this.pageSize
          }
        }
      ).then((res)=>{
        this.showKindList=res.data.records
        this.pages=res.data.pages
        this.total=res.data.total
      })
    },
    handleDelete(index,row){

      this.$confirm('此操作将永久删除该配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post({
            url:'/api/kind/deleteKind',
            data:{
              id:row.kindId
            }
          }
        )
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        this.getData()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    },
    handleSearch(){
      this.$post({
          url:'/api/kind/queryKind',
          data:{
            kind:this.search,
            page:this.page,
            pageSize:this.pageSize
          }
        }
      ).then((res)=>{
        this.showKindList=res.data.records
      })

    },
    handleRemake(){
      this.search=''
      this.getData()
    },


  }
}
</script>

<style scoped>

</style>
