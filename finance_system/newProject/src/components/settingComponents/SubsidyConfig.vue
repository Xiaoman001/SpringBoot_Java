<template>
<div>
  <el-container >
    <el-header align="right">
      <el-input v-model="search" size="small" style="width: 200px; right: 15px;position: relative" placeholder="查询" />
      <el-button size="small" type="primary" @click="handleSearch" style="position:relative;">查询</el-button>
      <el-button size="small" @click="handleRemake" style="position:relative;">重置</el-button>
      <el-button size="small" type="primary" @click="showAddDestination" style="position:relative; right: 10px">新建</el-button>
    </el-header>
    <el-main>
      <el-table :data="subsidyList" height="525">
        <el-table-column label="目的地" prop="city" />
        <el-table-column label="补助费用" prop="cost" />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button size="small" @click="showUpdateDestination(scope.row)">编辑</el-button>
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
  </el-container>
  <add-destination ref="addDestination"></add-destination>
  <update-destination ref="updateDestination"></update-destination>
</div>
</template>
<script>
import addDestination from './SubsidyConfiConponents/addDestination.vue'
import updateDestination from './SubsidyConfiConponents/updateDestination.vue'
export default {
  name: "SubsidyConfig",
  components:{
    addDestination,updateDestination
  },
  data (){
    return{
      id:'',
      subsidyList:[],
      search:'',
      formLabelWidth: '100px',
      page:1,
      pageSize:7,
      pages:2,
      total:0,
    }
  },
  mounted() {
    this.getData()
  },
  methods:{
    showAddDestination(){
      this.$refs.addDestination.showDialog()
    },
    showUpdateDestination(row){
      this.$refs.updateDestination.showDialog(row)
    },
    getData(){
      this.$post({
          url:'/api/subsidy/findSubsidy',
          data:{
            page:this.page,
            pageSize:this.pageSize
          }
        }
      ).then((res)=>{
        this.subsidyList=res.data.records
        this.pages=res.data.pages
        this.total=res.data.total
      })
    },
    handleUpload(){
    },
    handleDelete(index,row){
      this.$confirm('此操作将永久删除该配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post({
            url:'/api/subsidy/deleteSubsidy',
            data:{
              id:row.subsidyId
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
          url:'/api/subsidy/findSubsidy',
          data:{
            city:this.search,
            pageSize:this.pageSize,
            page:this.page,
          }
        }
      ).then((res)=>{
        this.subsidyList=res.data.records
      })
    },
    handleRemake(){
      this.search=''
      this.getData()
    },
    handleCurrentChange(currentPage){
      this.page = currentPage;//点击第几页
      this.getData()
    }
  }
}
</script>
<style scoped>
</style>
