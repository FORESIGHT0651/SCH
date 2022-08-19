<template>
  <div>
    <!--导航-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>
        <a href="/">查询管理</a>
      </el-breadcrumb-item>
      <el-breadcrumb-item>历史路线查询</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图-->
    <el-card>
      <!--搜索-->
      <el-row>
        <el-col :span="7">
          <el-input placeholder="请输入运单编号" v-model="findinput" clearable>
            <el-button slot="append" icon="el-icon-search" @click="findgood()"></el-button>
          </el-input>
        </el-col>
      </el-row>
      <!--表格-->
      <el-table :data="tableData" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="routeid" label="运单编号"></el-table-column>
        <el-table-column prop="username" label="司机"></el-table-column>
        <el-table-column prop="carnumber" label="车牌号"></el-table-column>        
        <el-table-column prop="routestate" label="运单状态"></el-table-column>
        <el-table-column label="操作" width="230px">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-search"
              @click="toRoute(scope.row)"
            >路线回放</el-button>          
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
//import axios from 'axios'
export default {
  data() {
    return {
      pageSize:'1',
      total:'3',
      findinput: '',
      tableData: [{routeid:'11'}],
      formLabelWidth: "120px"
    };
  },

   methods: {
    //点击跳转
    toRoute(row) {
      this.$router.push({
        path:'/goodroutesearch',
        query:{
          routeid:row.routeid
        }
      });
    },

    //根据id查询
    findgood(){
      const _this = this
      if(this.findinput != ''){
        axios.get('http://localhost:8282/route/findVRouteByRouteid/'+this.findinput).then(function(resp){
        console.log(resp)
        _this.tableData = resp.data;
        })
      }else{
        const _this = this;
        axios.get('http://localhost:8282/route/findVRoute').then(function(resp) {
        console.log(resp);
        _this.tableData = resp.data;
      });
      } 
    },
  },

  created() {
    const _this = this;
    axios.get('http://localhost:8282/route/findVRoute').then(function(resp) {
        console.log(resp);
        _this.tableData = resp.data;
      });
  }   
}
</script>

<style lang="sass" scoped>
</style>