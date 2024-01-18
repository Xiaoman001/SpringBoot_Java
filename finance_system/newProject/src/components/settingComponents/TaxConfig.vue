<template>
  <el-container >
    <el-header align="right">
      <el-input v-model="search" size="small" style="width: 200px; right: 15px;position: relative" placeholder="查询" />
      <el-button size="small" type="primary" @click="handleSearch" style="position:relative;">查询</el-button>
      <el-button size="small" @click="handleRemake" style="position:relative;">重置</el-button>
      <el-button size="small" type="primary" @click="showAdd" style="position:relative; right: 10px">新建</el-button>
    </el-header>

    <el-main>
      <el-table :data="TaxList" height="525">
        <el-table-column label="税务种类" prop="taxRateType" />
        <el-table-column label="税率" prop="taxRate" />
        <el-table-column label="操作" align="center">

          <template #default="scope">
            <el-button size="small" @click="showEdit(scope.$index, scope.row)">编辑</el-button>
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

    <add-tax @getData="getData" ref="addTax"></add-tax>
    <update-tax @getData="getData" ref="updateTax"></update-tax>

  </el-container>
</template>

<script>

import addTax from "./TaxConfigComponents/addTax";
import updateTax from "./TaxConfigComponents/updateTax";
export default {
  name: "TaxConfig",
  components: {updateTax, addTax},
  data (){
    return{
      id:'',
      TaxList:[

      ],
      search:'',
      deleteFromVisible:false,
      formLabelWidth: '100px',
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
    getData(){
      this.$post({
          url:'/api/tax/queryTaxRate',
          data:{
            page:this.page,
            pageSize:this.pageSize
          }
        }
      ).then((res)=>{
        this.TaxList=res.data.records
        this.pages=res.data.pages
        this.total=res.data.total
      })
    }
    ,
    showAdd(){
      this.$refs.addTax.showAdd()
    },
    showEdit(index,row){
      this.$refs.updateTax.showEdit(row)
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
            url:'/api/tax/deleteTaxRate',
            data:{
              id:row.taxRateId
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
          url:'/api/tax/queryTaxRate',
          data:{
            taxRateType:this.search,
            page:this.page,
            pageSize:this.pageSize
          }
        }
      ).then((res)=>{
        this.TaxList=res.data.records
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
