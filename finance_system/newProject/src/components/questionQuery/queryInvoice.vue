<template>
  <div>
      <el-container>
      <el-header >
        <el-row :gutter="0" justify="right" >
          <el-col :span="12" :push="1"><div class="grid-content bg-purple">
            <el-input v-model="search" size="small" style="width: 200px; " placeholder="请输入税号" />
            <el-button size="small" type="primary" @click="handleSearch" >查询</el-button>
          </div></el-col>
        </el-row>
      </el-header>

      <el-main>
        <el-table :data="searchTable" stripe style="width: 100%" height="525">
          <el-table-column label="发票类型" prop="invoiceKind" :width='150'></el-table-column>
          <el-table-column label="开票时间" prop="invoiceTime" :width='100'>
            <template slot-scope="scope">
              <span>{{ parseDateFull(scope.row.invoiceTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="分类" prop="kind" :width='100'></el-table-column>
          <el-table-column label="税价合计" prop="cost" :width='100'></el-table-column>
          <el-table-column label="状态" prop="stage" :width='150' :formatter="formatterColumn"></el-table-column>
          <el-table-column label="用户ID" prop="usrId" :width='100'></el-table-column>
          <el-table-column label="校验码" prop="checkCode" :min-width='150' align="center"></el-table-column>
           <el-table-column label="发票号码" prop="invoiceCode" :min-width='150' align="center"></el-table-column>
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
  </div>
</template>

<script>
export default {
    data(){
        return{
            search:'',
            page:1,
            pageSize:7,
            total:0,
            searchTable:[],
        }
    },
    methods:{
        handleSearch(){
            this.$post({
                url: "/api/invoice/queryInvoice",
                data: {
                    page:this.page,
                    pageSize:this.pageSize,
                    invoiceCode:this.search
                },
            }).then((res) => {
                if(res.code=='200'){
                    this.searchTable=res.data.records
                    this.total=res.data.total
                }     
            });
        },
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
        handleCurrentChange(currentPage){
            this.page=currentPage
            this.handleSearch()
        }
    },
    mounted(){
      this.handleSearch()
    }
}
</script>

<style>

</style>