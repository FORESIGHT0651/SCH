<template>
  <div>
    <div style="width:100%;height:50px;background:#fff;">
      <el-input style="width: 20px;top:5px;" v-model="carid" readonly type="hidden"></el-input>
      运单号
      <el-input style="width: 120px;top:5px;" v-model="routeid" readonly :disabled="true"></el-input>&#8195;&#8195;
      车牌号
      <el-input style="width: 120px;top:5px;" v-model="carnumber" readonly :disabled="true"></el-input>&#8195;&#8195;
      车空余重量量(公斤)
      <el-input style="width: 120px;top:5px;" v-model="weight" readonly :disabled="true"></el-input>&#8195;&#8195;
      车空余体积(m³)
      <el-input style="width: 120px;top:5px;" v-model="volume" readonly :disabled="true"></el-input>&#8195;&#8195;
      <el-button style="top:5px;" type="primary" icon="el-icon-edit" @click="deleteroute">清空路线</el-button>
    </div>
    <div id="amap-container" style="width:100%; height: 600px;"></div>
    <!--卡片视图-->
    <!-- <el-card style="width:20%;height:600px;position:absolute;right:35px;top:131px;background:#fff;"></el-card> -->
    <el-tabs
      type="border-card"
      style="width:23%;height:600px;position:absolute;right:22px;top:130px;background:#fff;"
    >
      <el-tab-pane label="运单信息">
        <el-button size="mini" type="primary" icon="el-icon-search" @click="toroute">路线显示</el-button>
        <el-button size="mini" type="warning" icon="el-icon-edit" @click="togood">查询订单</el-button>
        <el-button size="mini" type="primary" icon="el-icon-search" @click="toview">详情</el-button>
        <br />
        <br />
        <el-input v-model="addr" :rows="2" type="textarea" placeholder="路线顺序" readonly></el-input>
        <br />
        <br />
        <el-row>
          <el-col :span="25">
            <el-input placeholder="请输入运单编号" v-model="findrouteinput" clearable>
              <el-button slot="append" icon="el-icon-search" @click="findroute()"></el-button>
            </el-input>
          </el-col>
        </el-row>
        <!--表格运单-->
        <el-table
          ref="singleTable"
          :data="tableData1"
          height="350"
          highlight-current-row
          @current-change="handleCurrentChange"
          @row-click="chooseroute"
        >
          <el-table-column prop="routeid" label="运单编号"></el-table-column>
          <el-table-column prop="carid" label="车辆编号"></el-table-column>
          <el-table-column prop="routestate" label="运单状态"></el-table-column>
          <el-table-column prop="routeweight" label="总重"></el-table-column>
          <el-table-column prop="routevolume" label="体积"></el-table-column>
        </el-table>

        <!-- 路线上的订单对话框 -->
        <el-dialog title="该条路线上的未分配订单" :visible.sync="dialogVisible" width="40%">
          <el-table :data="tableData2" height="250">
            <el-table-column property="goodid" label="订单编号"></el-table-column>
            <el-table-column property="goodweight" label="重量"></el-table-column>
            <el-table-column property="goodvolume" label="体积"></el-table-column>
            <el-table-column property="goodnum" label="件数"></el-table-column>
            <el-table-column property="startaddr" label="起始地"></el-table-column>
            <el-table-column property="endaddr" label="目的地"></el-table-column>
            <el-table-column prop="goodtime" label="限定时间"></el-table-column>
            <el-table-column label="操作" fixed="right">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  icon="el-icon-search"
                  @click="insertRoute(scope.row)"
                >插入路线</el-button>
              </template>
            </el-table-column>
          </el-table>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">关 闭</el-button>
          </span>
        </el-dialog>

        <!-- 运单详情对话框 -->
        <el-dialog title="运单详情" :visible.sync="dialogVisible2" width="30%">
          路线编号
          <el-input style="width: 120px;top:5px;" v-model="routeiddetail" readonly></el-input>&#8195;&#8195;
          车 牌 号
          <el-input style="width: 120px;top:5px;" v-model="carnumberdetail" readonly></el-input>
          <br />
          <br />司 机
          <el-input style="width: 120px;top:5px;" v-model="usernamedetail" readonly></el-input>&#8195;&#8195;
          重 量
          <el-input style="width: 120px;top:5px;" v-model="routeweightdetail" readonly></el-input>
          <br />
          <br />体 积
          <el-input style="width: 120px;top:5px;" v-model="routevolumedetail" readonly></el-input>&#8195;&#8195;
          商品件数
          <el-input style="width: 120px;top:5px;" v-model="routetotaldetail" readonly></el-input>
          <br />
          <br />运单生成时间
          <el-input style="width: 240px;top:5px;" v-model="routetimedetail" readonly></el-input>
          <br />
          <el-table :data="tableDatadetail" height="200">
            <el-table-column prop="goodid" label="订单编号"></el-table-column>
            <el-table-column prop="goodtime" label="限定时间"></el-table-column>
            <el-table-column prop="startaddr" label="起始地"></el-table-column>
            <el-table-column prop="endaddr" label="目的地"></el-table-column>
          </el-table>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible2 = false">关 闭</el-button>
          </span>
        </el-dialog>
      </el-tab-pane>

      <el-tab-pane label="未分配订单">
        <el-select v-model="startlbsfind" clearable placeholder="请选择起送区域">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>&#8195;&#8195;
        <el-select v-model="endlbsfind" clearable placeholder="请选择终点区域">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>&#8195;&#8195;
        <el-button circle type="primary" icon="el-icon-search" @click="tofind()"></el-button>
        <!--表格-->
        <el-table :data="tableData3" height="430">
          <el-table-column prop="goodid" label="订单编号"></el-table-column>
          <el-table-column prop="startaddr" label="起始地"></el-table-column>
          <el-table-column prop="endaddr" label="目的地"></el-table-column>
          <el-table-column prop="goodtime" label="限定时间"></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import AMap from "AMap";
let lodash = require("lodash");
var map;
export default {
  data() {
    return {
      carid:'',
      addr: "",
      routeid: "",
      carnumber: "",
      weight: "",
      volume: "",
      dialogVisible: false,
      dialogVisible2: false,
      findrouteinput: "",
      startlbsfind: "",
      endlbsfind: "",
      routeiddetail: "",
      carnumberdetail: "",
      usernamedetail: "",
      routeweightdetail: "",
      routevolumedetail: "",
      routetotaldetail: "",
      routetimedetail: "",
      tableDatadetail: [],
      tableData2: [],
      tableData1: [
        {
          routeid: "121",
          carnumber: "桂A222222",
          routestate: "配送中"
        },
        {
          routeid: "121",
          carnumber: "桂A222222",
          routestate: "配送中"
        }
      ],
      tableData3: [
        {
          goodid: "101",
          goodtime: "2020-5-9 00:00:00",
          startaddr: "aaa",
          endaddr: "bbb"
        }
      ],
      options: [
        {
          value: "西乡塘西大区",
          label: "西乡塘西大区"
        },
        {
          value: "西乡塘二区",
          label: "西乡塘二区"
        },
        {
          value: "兴宁一区",
          label: "兴宁一区"
        },
        {
          value: "江南一区",
          label: "江南二区"
        },
        {
          value: "良庆一区",
          label: "良庆一区"
        },
        {
          value: "青秀一区",
          label: "青秀一区"
        }
      ]
    };
  },
  mounted() {
    this.initAMap();
  },

  methods: {
    //地图初始化
    initAMap: function() {
      map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 12, // 级别
        center: [108.413388, 22.842119], // 中心点坐标
        viewMode: "3D" //使用3D视图
      });
      AMap.plugin(["AMap.ToolBar", "AMap.Scale", "AMap.Driving"], function() {
        //地图定位+工具条
        map.addControl(new AMap.ToolBar()); //添加工具条插件
        map.addControl(new AMap.Scale());
      });
    },

    //单选运单
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.routeid = "";
      this.carnumber = "";
      this.weight = 0;
      this.volume = 0;
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    chooseroute(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.routeid = row.routeid;
      this.carid = row.carid;
      const _this = this;
      axios
        .get("http://localhost:8282/car/findCarByCarid/" + row.carid)
        .then(function(resp) {
          console.log(resp);
          _this.carnumber = resp.data[0].carnumber;
          _this.weight =
            parseFloat(resp.data[0].carweight * 1000 * resp.data[0].carrate) -
            parseFloat(row.routeweight);
          _this.volume =
            parseFloat(resp.data[0].carvolume * resp.data[0].carrate) -
            parseFloat(row.routevolume);
        });
    },

    //根据运单编号查运单
    findroute() {
      if (this.findrouteinput != "") {
        const _this = this;
        axios
          .get(
            "http://localhost:8282/route/findRouteByRoutestateAndRouteid/" +
              this.findrouteinput
          )
          .then(function(resp) {
            console.log(resp);
            _this.tableData1 = resp.data;
          });
      } else {
        const _this = this;
        axios
          .get("http://localhost:8282/route/findRouteByRoutestate")
          .then(function(resp) {
            console.log(resp);
            _this.tableData1 = resp.data;
          });
      }
    },

    //未分配运单查询
    tofind() {
      if (this.startlbsfind != "" && this.endlbsfind == "") {
        const _this = this;
        axios
          .get(
            "http://localhost:8282/good/findGoodByStartlbs/" + this.startlbsfind
          )
          .then(function(resp) {
            console.log(resp);
            _this.tableData3 = resp.data;
          });
      } else if (this.startlbsfind == "" && this.endlbsfind != "") {
        const _this = this;
        axios
          .get("http://localhost:8282/good/findGoodByEndlbs/" + this.endlbsfind)
          .then(function(resp) {
            console.log(resp);
            _this.tableData3 = resp.data;
          });
      } else if (this.startlbsfind != "" && this.endlbsfind != "") {
        const _this = this;
        axios
          .get(
            "http://localhost:8282/good/findGoodByStartlbsAndEndlbs/" +
              this.startlbsfind +
              "&" +
              this.endlbsfind
          )
          .then(function(resp) {
            console.log(resp);
            _this.tableData3 = resp.data;
          });
      } else if (this.startlbsfind == "" && this.endlbsfind == "") {
        const _this = this;
        axios
          .get("http://localhost:8282/good/findGoodByGoodstate")
          .then(function(resp) {
            console.log(resp);
            _this.tableData3 = resp.data;
          });
      }
    },

    //详情
    toview() {
      if (this.routeid != "") {
        this.dialogVisible2 = true;
        const _this = this;
        axios
          .get("http://localhost:8282/good/findGoodByRouteid/" + this.routeid)
          .then(function(resp) {
            console.log(resp);
            _this.tableDatadetail = resp.data;
          });

        axios
          .get(
            "http://localhost:8282/route/findVRouteByRouteid/" + this.routeid
          )
          .then(function(resp) {
            console.log(resp);
            _this.routeiddetail = resp.data[0].routeid;
            _this.carnumberdetail = resp.data[0].carnumber;
            _this.usernamedetail = resp.data[0].username;
            _this.routeweightdetail = resp.data[0].routeweight;
            _this.routevolumedetail = resp.data[0].routevolume;
            _this.routetotaldetail = resp.data[0].routetotal;
            _this.routetimedetail = resp.data[0].routetime;
          });
      } else {
        alert("请先选择运单！");
      }
    },

    //路线显示
    toroute() {
      console.log(this.routeid)
      console.log(this.carnumber)
      if(this.routeid != ''){
        var path1 = [],
        path = [],
        path3 = [];
      var mar = [];
      var marker3;
      const _this = this;
      //绘线
      AMap.plugin(["AMap.Driving", "AMap.Geocoder"], function() {
        axios.get("http://localhost:8282/teminfo/findTeminfoByRouteid/" + _this.routeid).then(function(resp) {
            console.log(resp);
            _this.addr = resp.data[0].teminfoaddr;
            path1 = resp.data[0].teminfoaddr.split(",");
            console.log(path1);
            for (var i = 0; i < path1.length; i++) {
              path[i] = {keyword:path1[i]}
             // path.push({ keyword: path1[i] });
            }
            var driving = new AMap.Driving({
              policy: resp.data[0].strategy,
              map: map
            });
            driving.search(path, function(status, result) {
              console.log(status);
              if (status === "complete") {
                //点标记
                var viaicon = new AMap.Icon({
                  size: new AMap.Size(27, 37),
                  Image:"//a.amap.com/jsapi_demos/static/demo-center/icons/dir-via-marker.png",
                  ImageSize: new AMap.Size(135, 40)
                });
                path3 = resp.data[0].teminfoaddr.split(";");
                var geocoder = new AMap.Geocoder({
                  // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                  city: "全国",
                  radius: 1000,
                  extensions: "all"
                });
                geocoder.getLocation(path3, function(status, result) {
                  if (status === "complete" && result.info === "OK") {
                    // result中对应详细地理坐标信息
                    console.log(result);
                    for (var j = 1; j < result.geocodes.length - 1; j++) {
                      mar = [
                        result.geocodes[j].location.lng,
                        result.geocodes[j].location.lat
                      ];
                      marker3 = new AMap.Marker({
                        map: map,
                        position: mar,
                        icon: viaicon,
                        offset: new AMap.Pixel(-14, -35),
                        autoRotation: true,
                        label: {
                          offset: new AMap.Pixel(20, 20),
                          content: "经点" + parseInt(j) + "：" + path1[j]
                        }
                      });
                      map.add(marker3);
                    }
                  }
                });
              }
            });
        });

        var geocoder = new AMap.Geocoder({
          radius: 1000,
          extensions: "all"
        });
        var lnglatXY = [];
        axios.get("http://localhost:8282/car/findCarByCarnumberOrder/" +_this.carnumber).then(function(resp) {
            console.log(resp);
            lnglatXY = [];
            geocoder.getLocation(resp.data[0].caraddr, function(status,result) {
              if (status === "complete" && result.info === "OK") {
                console.log(result);
                lnglatXY.push(
                  result.geocodes[0].location.lng,
                  result.geocodes[0].location.lat
                );
                console.log(lnglatXY);

                //构造点标记
                var marker = new AMap.Marker({
                  icon: "https://webapi.amap.com/images/car.png",
                  //icon:'https://webapi.amap.com/theme/v1.3/markers/b/mark_b.png',
                  map: map,
                  position: lnglatXY
                });
                //构造信息窗体
                var infoWindow = new AMap.InfoWindow({
                  //isCustom: true,
                  content:
                    "<div style='height:30px;text-align:center;font-size:20px'>车辆位置信息</div>" +
                    "<div>车牌号：" +
                    _this.carnumber +
                    "<br/>当前位置：" +
                    result.geocodes[0].formattedAddress +
                    "<br/>当前区域：" +
                    resp.data[0].carlbs +
                    "</div>",
                  offset: new AMap.Pixel(12, -30)
                });
               // map.setFitView();
                map.add(marker);
                infoWindow.open(map, marker.getPosition());
              }
            });
         });
      });
      }else{
        alert("请先选择运单！")
      }
    },

    //查单
    togood() {
      if(this.routeid != ''){
        var patharr = [],
        pathlist = [];
      const _this = this;
      axios.get("http://localhost:8282/teminfo/findTeminfoByRouteid/" + this.routeid
        ).then(function(resp) {
          console.log(resp);
          patharr = resp.data[0].teminfoaddr.split(",");
          for (var i = 0; i < patharr.length; i++) {
            for (var j = i; j < patharr.length; j++) {
              axios.get("http://localhost:8282/good/findGoodByAddrAndState/" +
                    patharr[i] +
                    "&" +
                    patharr[j]
                ).then(function(res) {
                  console.log(res);
                  if (res.data.length != 0) {
                    console.log(res.data);
                    pathlist = lodash.cloneDeep(res.data)
                  }
                  _this.tableData2 = pathlist
                });
            }
          }
        });
      this.dialogVisible = true;
      }else{
        alert("请先选择运单！")
      }
      
    },

    //插入路线
    insertRoute(row) {
      console.log(row.goodid);
      if((row.goodweight*row.goodnum)<this.weight && (row.goodvolume*row.goodnum)<this.volume){
        var weight1=0,volume1=0;
        weight1 = this.weight;
        volume1 = this.volume;
        const _this = this;
        axios.put('http://localhost:8282/good/updateGoodRouteid/'+this.routeid+'&'+row.goodid)
         .then(function(resp) {
            console.log(resp);
            if(resp.data == "success"){
              //更新订单表
              axios.get("http://localhost:8282/route/findRouteByRoutestate")
                .then(function(res) {
                console.log(res);
               _this.tableData1 = res.data;
              });
              //更新运单重量和容积
              axios.get("http://localhost:8282/car/findCarByCarid/" + _this.carid)
                .then(function(res) {
                console.log(res);
                _this.weight = parseFloat(weight1) - parseFloat(row.goodweight);
                _this.volume =  parseFloat(volume1) - parseFloat(row.goodvolume);
              });
              _this.$message({
                type: "success",
                message: "插入成功！"
             });
             _this.dialogVisible = false;
            }
          });
      }else{
        this.$message({
          type: "error",
          message: "车货不匹配，插入失败！"
        });
      }
      
    },

    //清空路线
    deleteroute(){
      map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 12, // 级别
        center: [108.413388, 22.842119], // 中心点坐标
        viewMode: "3D" //使用3D视图
      });
      AMap.plugin(["AMap.ToolBar", "AMap.Scale", "AMap.Driving"], function() {
        //地图定位+工具条
        map.addControl(new AMap.ToolBar()); //添加工具条插件
        map.addControl(new AMap.Scale());
      });
      this.addr= "";
      this.routeid="";
      this.carnumber= "";
      this.weight="";
      this.volume="";
    }
  },

  created() {
    const _this = this;
    axios.get("http://localhost:8282/route/findRouteByRoutestate")
      .then(function(resp) {
        console.log(resp);
        _this.tableData1 = resp.data;
      });

    axios.get("http://localhost:8282/good/findGoodByGoodstate")
      .then(function(resp) {
        console.log(resp);
        _this.tableData3 = resp.data;
      });
  }
};
</script>