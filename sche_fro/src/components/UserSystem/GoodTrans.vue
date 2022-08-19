<template>
  <div>
    <!--导航-->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>
        <a href="/">任务管理</a>
      </el-breadcrumb-item>
      <el-breadcrumb-item>订单配送任务</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片视图-->
    <el-card>
      <el-input style="width: 20px;top:5px;" v-model="routeidcheck" readonly type="hidden"></el-input>
      <!--表格-->
      <el-table :data="tableData" stripe border>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="routeid" label="运单编号"></el-table-column>
        <el-table-column prop="carid" label="车辆编号"></el-table-column>
        <el-table-column prop="carnumber" label="车牌号"></el-table-column>
        <el-table-column prop="routeweight" label="总重量"></el-table-column>
        <el-table-column prop="routevolume" label="体积"></el-table-column>
        <el-table-column prop="routenum" label="订单数"></el-table-column>
        <el-table-column prop="routetotal" label="总件数"></el-table-column>
        <el-table-column prop="routetime" label="运单建立时间"></el-table-column>
        <el-table-column prop="routestate" label="状态"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-check"
              @click="tojiedan(scope.row)"
              :disabled="scope.row.routestate == '配送中'"
            >接单</el-button>
            <el-button
              size="mini"
              type="warning"
              icon="el-icon-close"
              @click="tojujue"
              :disabled="scope.row.routestate == '配送中'"
            >拒绝</el-button>
            <el-button
              size="mini"
              type="success"
              icon="el-icon-s-promotion"
              @click="toTrans(scope.row)"
              :disabled="scope.row.routestate == '待配送'"
            >配送</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

     <!-- 拒绝接单记录对话框 -->
      <el-dialog title="提示" :visible.sync="dialogVisible2" width="30%" >
        <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入原因"
            v-model="textarea"
            maxlength="500"
            show-word-limit
            clearable>
         </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="dialogjujue">确 认</el-button>
        </span>
      </el-dialog>
  </div>
</template>
<script>
//import axios from 'axios'
export default {
  data() {
    return {
      textarea:'',
      findinput: "",
      tableData: [{}],
      formLabelWidth: "120px",
      dialogVisible2:false,
      routeidcheck:'',
    };
  },

  methods: {
    //接单
    tojiedan(row){
      const _this = this;
      axios.put("http://localhost:8282/route/updateRoutestateDeal/" + row.routeid)
        .then(function(resp) {
          console.log(resp.data);
          if (resp.data === "success") {
            axios.get("http://localhost:8282/route/findVRouteByUser/" +_this.$session.get("useraccount")).then(function(res) {
              console.log(res);
              _this.tableData = res.data;
            });
            _this.$message({
              type: "success",
              message: "接单成功！"
            });
          }
        });
    },
    //拒绝
    tojujue(row) {
      this.routeidcheck = row.routeidid;
      this.dialogVisible2 = true;
        
    },
    dialogjujue(){
      this.$message({
          type: "success",
          message: "已拒绝接单！"
        });
      this.dialogVisible2 = false;
    },
    //点击分配跳转
    toTrans(row) {
        this.$router.push({
            path: "/routedelivery",
            query: {
              routeid: row.routeid
            }
        });
    },

  },

  created() {
    const _this = this;
    axios
      .get(
        "http://localhost:8282/route/findVRouteByUser/" +
          this.$session.get("useraccount")
      )
      .then(function(resp) {
        console.log(resp);
        _this.tableData = resp.data;
      });
  }
};
</script>
<style lang="sass" scoped>
</style>