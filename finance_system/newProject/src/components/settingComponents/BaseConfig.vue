<template>
    <div id="baseConfig">
      <el-header align="right">
        <el-input v-model="search" size="small" style="width: 200px; position:relative;"  placeholder="查询" />
        <el-button size="small" type="primary" @click="handleSearch" style="position:relative;" >查询</el-button>
        <el-button size="small" @click="handleRemake" style="position:relative;">重置</el-button>
        <el-button size="small" type="primary" @click="showAddBase" style="position: relative;right: 10px">新建</el-button>
      </el-header>

      <el-main>
        <el-table :data="showBaseList" height="525" >
          <el-table-column label="BASE地" prop="city" />
          <el-table-column label="详细地址" prop="address" />
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button size="small" @click="showUpdateBase(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="deleteConfirm(scope.row)">删除</el-button>
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
        <add-base ref="addBase"></add-base>
        <update-base ref="updateBase"></update-base>
    </div>
</template>

<script>
import addBase from './BaseConfigComponents/addBase.vue'
import updateBase from './BaseConfigComponents/updateBase.vue'
export default {
  name: "BaseConfig",
  components:{
    addBase,updateBase
  },
  data (){
    return{
      showBaseList:[],
      search:'',
      page:1,
      pageSize:7,
      pages:2,
      total:0,

    }
  },
  mounted() {
      this.getData();
  },
  methods:{
    handleCurrentChange(currentPage){
      this.page = currentPage;//点击第几页
      this.getData()
    }
    ,
    showAddBase(){
        this.$refs.addBase.showDialog();
    },
    showUpdateBase(row){
        this.$refs.updateBase.showDialog(row);
    },
    getData(){
      this.$post({
          url:'/api/base/queryBase',
          data:{
            page:this.page,
            pageSize:this.pageSize
          }
        }
      ).then((res)=>{
        this.showBaseList=res.data.records
        this.pages=res.data.pages
        this.total=res.data.total
      })
    },
    deleteConfirm(row){
      this.$confirm('此操作将永久删除该配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post({
            url:'/api/base/deleteBase',
            data:{
              id:row.baseId
            }
          }
        )
        this.getData();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSearch(){
      this.page=1
      this.$post({
          url:'/api/base/queryBase',
          data:{
            page:this.page,
            pageSize:this.pageSize,
            address:this.search
          }
        }
      ).then((res)=>{
        this.showBaseList=res.data.records
      })

    },
    handleRemake(){
      this.search=''
      this.page=1
      this.$post({
          url:'/api/base/queryBase',
          data:{
            page:this.page,
            pageSize:this.pageSize,
          }
        }
      ).then((res)=>{
        this.showBaseList=res.data.records
      })
    },
  }
}
</script>
<style scoped>
</style>
