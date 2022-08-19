<template>
  <div>
    <span id="positionres" class="positionres" style="display:none" value></span>
    <div style="width:100%;height:50px;background:#fff;">
      <el-input style="width: 20px;top:5px;" v-model="goodidcheck" readonly type="hidden"></el-input>
      运单号
      <el-input style="width: 120px;top:5px;" v-model="routeid" readonly :disabled="true"></el-input>&#8195;&#8195;
      <el-button size="mini" type="primary" @click="toroute">结束运单</el-button>
      <el-button size="mini" type="warning" @click="updateroute">更改路线记录</el-button>
      <el-input style="width: 20px;top:5px;" v-model="temaddr" readonly type="hidden"></el-input>
      <!-- 结束运单对话框 -->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" >
        <span>请确认订单是否全部送达，运单{{this.routeid}}结束?</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogroute">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 更改路线记录对话框 -->
      <el-dialog title="提示" :visible.sync="dialogVisible2" width="30%" >
        <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入更改路线原因"
            v-model="textarea"
            maxlength="500"
            show-word-limit
            clearable>
         </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="dialogup">提 交</el-button>
        </span>
      </el-dialog>
    </div>
    <div id="amap-container" style="width:100%; height: 600px;"></div>
    <div id="panel" class="panel"></div>
    <div id="btn" class="btn">
      <el-card>
        <el-row>
          <el-col :span="25">
            <el-input placeholder="请输入订单编号" v-model="findinput" clearable>
              <el-button slot="append" icon="el-icon-search" @click="findroute()"></el-button>
            </el-input>
          </el-col>
        </el-row>
        <!--表格-->
        <el-table :data="tableData" border height="230">
          <el-table-column prop="goodid" label="订单编号"></el-table-column>
          <el-table-column prop="startaddr" label="起始地"></el-table-column>
          <el-table-column prop="endaddr" label="目的地"></el-table-column>
          <el-table-column prop="goodtime" label="限定时间"></el-table-column>
          <el-table-column prop="goodstate" label="状态"></el-table-column>
          <el-table-column label="操作" fixed="right">
            <template slot-scope="scope">
              <el-button id="goodend" size="mini" type="primary" @click="togood(scope.row)" :disabled="scope.row.goodstate != '处理中'">处理</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="提示" :visible.sync="dialogVisible1" width="30%" >
          <el-tabs v-model="activeName" @tab-click="handleClick">
             <el-tab-pane label="正常签收" name="first">
               <el-upload
                 class="upload-demo"
                 action="https://jsonplaceholder.typicode.com/posts/"
                 :on-preview="handlePreview"
                 :on-remove="handleRemove"
                 :before-remove="beforeRemove"
                  multiple
                 :file-list="fileList"
                 list-type="picture">
                  <el-button type="success">点击上传客户签单</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件</div>
                </el-upload><br/><br/>
               <el-button type="primary" @click="dialogVisible1 = false">取 消</el-button>
               <el-button type="primary" @click="dialoggood">确 定</el-button>
             </el-tab-pane>
             <el-tab-pane label="异常处理" name="second">
               <el-upload
                 class="upload-demo"
                 action="https://jsonplaceholder.typicode.com/posts/"
                 :on-preview="handlePreview1"
                 :on-remove="handleRemove1"
                 :before-remove="beforeRemove1"
                  multiple
                 :file-list="fileList1"
                 list-type="picture">
                  <el-button type="warning">点击上传异常签单</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件</div>
                </el-upload><br/><br/>
               <el-button type="primary" @click="dialogVisible1 = false">取 消</el-button>
               <el-button type="primary" @click="errorgood">确 定</el-button></el-tab-pane>
          </el-tabs>
          <span slot="footer" class="dialog-footer">
            
          </span>
        </el-dialog>
      </el-card>
    </div>
  </div>
</template>

<script>
import AMap from "AMap";
var map;
var path = [];
var route, marker;
export default {
  data() {
    return {
      activeName: 'first',
      fileList:"",
      fileList1:"",
      goodidcheck:'',
      routeid: "",
      temaddr:'',
      dialogVisible: false,
      dialogVisible1: false,
      dialogVisible2: false,
      positionres: "",
      findinput:'',
      tableData:[],
      textarea:'',
    };
  },
  mounted() {
    this.initAMap();
  },

  methods: {
       // inputValue(){
    //   if(this.vtime>'2020-4-1'){
    //     this.vtime = this.vtime.substring('2020-4-1',this.vtime.length-20);
    //   }
    // },
    //地图初始化
    initAMap: function() {
      map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 12, // 级别
        //center: [108.413388, 22.842119], // 中心点坐标
        viewMode: "3D" //使用3D视图
      });
      //加载插件
      AMap.plugin(
        [
          "AMap.ToolBar",
          "AMap.Scale",
          "AMap.Geolocation",
          "AMap.Autocomplete",
          "AMap.Driving"
        ],
        function() {
          var getlocation = new AMap.Geolocation({
            timeout: 6000, //设置定位超时时间6s
            GeoLocationFirst: false, //是否使用高精度定位，默认是true
            maximumAge: 0 //定位结果缓存0毫秒，默认0
          });
          //地图定位+工具条
          map.addControl(new AMap.ToolBar()); //添加工具条插件
          map.addControl(new AMap.Scale());          
      });
      var path1 = [],path = [];
      const _this = this;
      axios.get("http://localhost:8282/teminfo/findTeminfoByRouteid/" + this.routeid).then(function(resp) {
          console.log(resp);
          AMap.plugin(["AMap.Driving", "AMap.Geocoder"], function() {
            _this.addr = resp.data[0].teminfoaddr;
            path1 = resp.data[0].teminfoaddr.split(",");
            console.log(path1);
            for (var i = 0; i < path1.length; i++) {
              path[i] = {keyword:path1[i]}
             // path.push({ keyword: path1[i] });
            }
            var driving = new AMap.Driving({
              policy: resp.data[0].strategy,
              map: map,
              panel: "panel"
            });
            driving.search(path, function(status, result) {
              console.log(status);
              if (status === "complete") {
                console.log("绘制驾车路线完成");
              }else {
                console.log("获取驾车数据失败：" + result);
               }
            });
          })
      
      })
    },

    //更改路线记录
    updateroute(){
      this.dialogVisible2 = true;
    },
    dialogup(){
       this.$message({
              type: "success",
              message: "提交成功！"
        });
        this.dialogVisible2 = false;
    },

    //结束运单
    toroute() {
      this.dialogVisible = true;
    },
    dialogroute(){
      console.log(this.routeid)
      const _this = this
      axios.put('http://localhost:8282/route/updateRoutestateEnd/'+this.routeid)
        .then(function(resp) {
            console.log(resp);
            if(resp.data == "success"){
            _this.$router.push("/goodtrans");
            }
      })
    },
    
    //订单完成
    handleRemove(file, fileList) {
        console.log(file, fileList);
      },
    handlePreview(file) {
        console.log(file);
      },
    beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
    handleRemove1(file, fileList1) {
        console.log(file1, fileList1);
      },
    handlePreview1(file) {
        console.log(file);
      },
    beforeRemove1(file, fileList1) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
    togood(row) {
      console.log(row.goodid)
      this.goodidcheck = row.goodid;
      this.dialogVisible1 = true;
    },

    //确认订单完成
    dialoggood(){
      const _this = this
      axios.put('http://localhost:8282/good/updateGoodRoutestate/'+this.goodidcheck)
      .then(function(resp) {
            console.log(resp);
            if(resp.data == "success"){
                axios.get("http://localhost:8282/good/findGoodByRouteid/" + _this.routeid)
                .then(function(res) {
                   console.log(res);
                   _this.tableData = res.data;
                 });
                _this.$message({
                    type: "success",
                    message: "订单正常更新完成！"
                  });
                _this.dialogVisible1 = false;
                handleRemove(file, fileList);
            }else{
              console.log('error')
            }
      })
    },
    //订单异常
    errorgood(){
      const _this = this
      axios.put('http://localhost:8282/good/updateGoodstateError/'+this.goodidcheck)
      .then(function(resp) {
            console.log(resp);
            if(resp.data == "success"){
                axios.get("http://localhost:8282/good/findGoodByRouteid/" + _this.routeid)
                .then(function(res) {
                   console.log(res);
                   _this.tableData = res.data;
                 });
                _this.$message({
                    type: "success",
                    message: "订单异常更新完成！"
                  });
                _this.dialogVisible1 = false;
            }else{
              console.log('error')
            }
      })
    }

  },

  created(){
    this.routeid = this.$route.query.routeid;
    const _this = this;
      axios.get("http://localhost:8282/good/findGoodByRouteid/" + this.$route.query.routeid).then(function(resp) {
          console.log(resp);
          _this.tableData = resp.data;
      });
  }
};
</script>

<style lang="less" scoped>
.panel {
  position: absolute;
  background-color: white;
  height: 300px;
  width: 20%;
  top: 80px;
  right: 20px;
  float: left;
  overflow: auto;
}
.btn {
  position: absolute;
  background-color: white;
  width: 20%;
  top: 390px;
  right: 20px;
  float: left;
  overflow: auto;
}
.panel .amap-call {
  background-color: #009cf9;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
}
.panel .amap-lib-driving {
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
  overflow: hidden;
}
</style>