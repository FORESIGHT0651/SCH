<template>
  <div>
    <!--导航-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>
        <a href="/">查询管理</a>
      </el-breadcrumb-item>
      <el-breadcrumb-item>个人配送查询</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图-->
    <el-card>
       <!--搜索-->
      <el-row>
        <el-col :span="7">
          <el-input placeholder="请输入运单编号" v-model="findinput" clearable>
            <el-button slot="append" icon="el-icon-search" @click="findroute()"></el-button>
          </el-input>
        </el-col>
      </el-row>
      <!--表格-->
      <el-table ref="singleTable"
        :data="tableData1"
        height="250"
        stripe
        border
        highlight-current-row
        @current-change="handleCurrentChange"
        @row-click="chooseroute"
      >
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="routeid" label="运单编号"></el-table-column>
        <el-table-column prop="carnumber" label="车牌号"></el-table-column>
        <el-table-column prop="routeweight" label="总重量"></el-table-column>
        <el-table-column prop="routevolume" label="体积"></el-table-column>
        <el-table-column prop="routenum" label="订单数"></el-table-column>
        <el-table-column prop="routetotal" label="总件数"></el-table-column>
        <el-table-column prop="routetime" label="运单建立时间"></el-table-column>
        <el-table-column prop="routestate" label="状态"></el-table-column>
      </el-table>
      <br/>
      <!--表格订单-->
      <el-table
        :data="tableData2"
        height="250"
        border
      >
        <el-table-column prop="goodid" label="订单编号"></el-table-column>
        <el-table-column prop="goodweight" label="重量(公斤)"></el-table-column>
        <el-table-column prop="goodvolume" label="体积(m³)"></el-table-column>
        <el-table-column prop="goodnum" label="件数"></el-table-column>
        <el-table-column prop="startaddr" label="出发地"></el-table-column>
        <el-table-column prop="endaddr" label="目的地"></el-table-column>
        <el-table-column prop="startlbs" label="起始区域"></el-table-column>
        <el-table-column prop="endlbs" label="终点区域"></el-table-column>
        <el-table-column prop="goodtime" label="限时时间"></el-table-column>
        <el-table-column prop="goodstate" label="状态"></el-table-column>
      </el-table>
     
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      findinput:"",
      tableData1:[],
      tableData2:[],
    }
  },
  methods:{
    //单选
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.distcarid = "";
      this.distcarnumber = "";
      this.weight = 0;
      this.volume = 0;
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    chooseroute(row) {
      this.$refs.singleTable.setCurrentRow(row);
      const _this = this;
        axios.get('http://localhost:8282/good/findGoodByRouteid/'+row.routeid).then(function(resp) {
          console.log(resp);
          _this.tableData2 = resp.data;
        });
    },

    //查询运单
    findroute(){
      if(this.findinput != ''){
        const _this = this;
        axios.get('http://localhost:8282/route/findVRouteByRouteid/'+this.findinput).then(function(resp) {
          console.log(resp);
          _this.tableData1 = resp.data;
        });
      }else{
        const _this = this;
        axios.get('http://localhost:8282/route/findVRouteByRouteAndUser/'+this.$session.get("useraccount")).then(function(resp) {
          console.log(resp);
          _this.tableData1 = resp.data;
        });
      }
    }
  },

  created(){
    const _this = this;
    axios.get('http://localhost:8282/route/findVRouteByRouteAndUser/'+this.$session.get("useraccount")).then(function(resp) {
        console.log(resp);
         _this.tableData1 = resp.data;
    });
  }
}
</script>