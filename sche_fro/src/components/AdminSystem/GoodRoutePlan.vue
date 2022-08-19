<template>
  <div>
    <div style="width:100%;height:50px;background:#fff;">
      <el-input style="width: 5px;top:5px;" v-model="tempath1" readonly type="hidden"></el-input>
      运单号
      <el-input style="width: 120px;top:5px;" v-model="routeid" readonly :disabled="true"></el-input>&#8195;&#8195;
      车牌号
      <el-input style="width: 120px;top:5px;" v-model="carnumber" readonly :disabled="true"></el-input>&#8195;&#8195;
      车辆当前位置
      <el-input style="width: 120px;top:5px;" v-model="caraddr" readonly :disabled="true"></el-input>&#8195;&#8195;
      订单总重量(公斤)
      <el-input style="width: 120px;top:5px;" v-model="goodweight" readonly :disabled="true"></el-input>&#8195;&#8195;
      订单总体积(m³)
      <el-input style="width: 120px;top:5px;" v-model="goodvolume" readonly :disabled="true"></el-input>&#8195;&#8195;
      订单数(个)
      <el-input style="width: 100px;top:5px;" v-model="goodnum" readonly :disabled="true"></el-input>&#8195;&#8195;
    </div>
    <div id="amap-container" style="width:100%; height: 610px;"></div>

    <!--卡片视图-->
    <el-card style="width:22%;height:600px;position:absolute;right:28px;top:131px;background:#fff;">
      <el-select v-model="strategy" placeholder="请选择安排策略">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <br />
      <br />
      <el-button type="primary" @click="tocompute" size="mini">计算路线</el-button>
      <el-button type="primary" @click="todraw" size="mini">绘制路线</el-button>
      <el-button type="warning" @click="tosave" size="mini">保存路线</el-button>
      <!--  保存路线对话框 -->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" >
        保存路线成功！
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialog">确 定</el-button>
        </span>
      </el-dialog>
      <br />
      <br />
      <el-input v-model="addr" :rows="2" type="textarea" placeholder="路线顺序" readonly></el-input>总里程(公里)：
      <el-input v-model="routeway" readonly></el-input>预计使用时间：
      <el-input v-model="routetime" readonly></el-input>
      <!--表格-->
      <el-table :data="tableData" border height="280">
        <el-table-column prop="goodid" label="订单编号"></el-table-column>
        <el-table-column prop="startaddr" label="起始地"></el-table-column>
        <el-table-column prop="endaddr" label="目的地"></el-table-column>
        <el-table-column prop="goodtime" label="限定时间"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
import AMap from "AMap";
//import axios from 'axios'
let lodash = require("lodash");
var map;
var path = [
  // { keyword: "广西南宁市广西大学南门" },
  // { keyword: "广西南宁市动物园" },
  // { keyword: "广西南宁市药用植物园" },
  // { keyword: "广西南宁市广西工商学校" },
  // { keyword: "广西南宁市第三十九中学" },
  // { keyword: "广西南宁市新秀公园" },
  // { keyword: "广西南宁市狮山公园" },
];
var carstrategy = "";
var arr = [];
var lineArr = [];
var pathline = new Array();
var dis, dis2;
var pl = [];
var distanceArr = [];
var distArrend = [];
var datapath = [];

export default {
  data() {
    return {
      tempath1:'',
      dialogVisible: false,
      carnumber: "桂A123456",
      caraddr: "广西南宁市广西大学南门",
      routeid: "303",
      goodweight: "17890",
      goodvolume: "66.5",
      goodnum: "3",
      tableData: [
        {
          goodid: "11",
          startaddr: "广西南宁市动物园",
          endaddr: "广西南宁市药用植物园"
        },
        {
          goodid: "12",
          startaddr: "广西南宁市广西工商学校",
          endaddr: "广西南宁市第三十九中学"
        },
        {
          goodid: "13",
          startaddr: "广西南宁市广西工商学校",
          endaddr: "广西南宁市第三十九中学"
        }
      ],
      strategy: "最快捷模式",
      addr: "",
      routeway: "",
      routetime: "",
      options: [
        {
          value: "最快捷模式",
          label: "最快捷模式"
        },
        {
          value: "最经济模式",
          label: "最经济模式"
        },
        {
          value: "最短距离模式",
          label: "最短距离模式"
        },
        {
          value: "考虑实时路况",
          label: "考虑实时路况"
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

    //计算路线
    tocompute: function() {
      carstrategy = "";
      arr = [];
      lineArr = [];
      pathline = [];
      dis = 0;
      dis2 = 0;
      pl = [];
      distanceArr = [];
      if (this.strategy == "最快捷模式") {
        carstrategy = 0;
      } else if (this.strategy == "最经济模式") {
        carstrategy = 1;
      } else if (this.strategy == "最短距离模式") {
        carstrategy = 2;
      } else if (this.strategy == "考虑实时路况") {
        carstrategy = 4;
      }
      console.log(path);
      for (var i = 0; i < path.length; i++) {
        lineArr.push(path[i].keyword);
      }
      console.log(lineArr);
      arr = show(lineArr);
      function show(input) {
        var perm = [],
          usedChars = [];
        function main(input) {
          var ch;
          for (var j = 1; j < input.length; j++) {
            ch = input.splice(j, 1)[0];
            usedChars.push(ch);
            if (input.length == 1) {
              perm.push(usedChars.slice());
            }
            main(input);
            input.splice(j, 0, ch);
            usedChars.pop();
          }
          return perm;
        }
        return main(input);
      }
      for (var n = 0; n < arr.length; n++) {
        arr[n].splice(0, 0, path[0].keyword);
      }
      console.log(arr);

      AMap.plugin(
        ["AMap.Driving", "AMap.Geocoder", "AMap.GeometryUtil"],
        function() {
          var geocoder = new AMap.Geocoder({
            // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
            city: "全国",
            radius: 1000,
            extensions: "all"
          });

          for (var i = 0; i < arr.length; i++) {
            //console.log(point.length);
            geocoder.getLocation(arr[i], function(status, result) {
              if (status === "complete" && result.info === "OK") {
                // result中对应详细地理坐标信息
                console.log(result);
                // pathline.push(lodash.cloneDeep(result));
                pathline = [];
                for (var j = 0; j < result.geocodes.length; j++) {
                  pathline.push({
                    keyword: result.geocodes[j].formattedAddress,
                    lnglat: [
                      result.geocodes[j].location.lng,
                      result.geocodes[j].location.lat
                    ]
                    // lat: result.geocodes[j].location.lat
                  });
                }
                console.log(pathline);
                var ori1 = 0,
                  ori2,
                  dest1 = 0,
                  dest2,
                  wayp1 = [],
                  wayp2;
                ori1 = pathline[0].lnglat;
                ori2 = String(ori1);
                dest1 = pathline[pathline.length - 1].lnglat;
                dest2 = String(dest1);
                for (var k = 1; k < pathline.length - 1; k++) {
                  wayp1.push(pathline[k].lnglat);
                }
                wayp2 = wayp1.join(";");
                //console.log(wayp2);

                const _this = this;
                axios
                  .get("http://restapi.amap.com/v3/direction/driving", {
                    params: {
                      key: "f95df2993b172bccda27cb2597b3f07b",
                      origin: ori2,
                      destination: dest2,
                      waypoints: wayp2,
                      strategy: carstrategy
                    }
                  })
                  .then(function(resp) {
                    console.log(resp);

                    dis = resp.data.route.paths[0].distance;
                    pl = [];
                    for (var m = 0; m < result.geocodes.length; m++) {
                      pl.push(result.geocodes[m].formattedAddress);
                    }
                    distanceArr.push({
                      keyword: pl,
                      distance: dis,
                      step: resp.data.route.paths[0].steps,
                      distance: resp.data.route.paths[0].distance,
                      duration: resp.data.route.paths[0].duration
                    });
                  });

                return distanceArr;
              }
            });
          }
        }
      );

      this.$message({
        type: "success",
        message: "计算路线成功！"
      });
    },

    //绘制路线
    todraw() {
      console.log(distanceArr);
      if (distanceArr.length === 0) {
        alert("请先计算路线");
      } else {
        var distArrend2 = [];
        dis2 = distanceArr[0].distance;
        distArrend.push({ keyword: distanceArr[0].keyword, distance: dis2 });
        console.log(dis2);
        for (var b = 0; b < distanceArr.length; b++) {
          if (distanceArr[b].distance < dis2) {
            dis2 = distanceArr[b].distance;
            distArrend = [];
            distArrend.push({
              keyword: distanceArr[b].keyword,
              distance: dis2,
              step: distanceArr[b].step,
              distance: distanceArr[b].distance,
              duration: distanceArr[b].duration
            });
          }
        }
        //console.log(distArrend);
        //console.log(distArrend[0].keyword.join(";"));
        this.addr = distArrend[0].keyword.join(";\n\n");
        this.routeway = distArrend[0].distance / 1000;
        var htime = parseInt(distArrend[0].duration / 3600);
        var mintime = parseInt(distArrend[0].duration / 60 - htime * 60);
        this.routetime = htime + "小时" + mintime + "分";
        // this.routetime =distArrend[0].duration
        var lnglatarr = [];
        for (var i = 0; i < distArrend[0].step.length; i++) {
          lnglatarr.push(distArrend[0].step[i].polyline);
        }
        //console.log(lnglatarr)
        var lnglatarr2,
          lnglatpath = [];
        for (var j = 0; j < lnglatarr.length; j++) {
          lnglatarr2 = lnglatarr.join(";").split(";");
        }
        for (var a = 0; a < lnglatarr2.length; a++) {
          lnglatpath = lnglatarr2.join(",").split(",");
        }
        var lnglatpath2 = lnglatpath.map(Number);
        // console.log(lnglatpath2)
        datapath = [];
        for (var s = 0; s < Math.ceil(lnglatpath2.length / 2); s++) {
          datapath[s] = [];
          datapath[s].push(lnglatpath2[s * 2]);
          datapath[s].push(lnglatpath2[s * 2 + 1]);
        }
        //console.log(datapath);
        // return lnglatpath

        console.log(distArrend[0].keyword);
       // console.log(distArrend2[0].keyword);
       var distArrend3=[]
        for(var i=0;i<distArrend[0].keyword.length;i++){
          console.log(i)
          console.log(distArrend[0].keyword[i]);
          distArrend2[i] = {keyword:distArrend[0].keyword[i]}
          distArrend3[i] = {keyword:distArrend[0].keyword[i]}
        }
        console.log(distArrend3);
        //起点标记
        var starticon = new AMap.Icon({
          size: new AMap.Size(25, 34),
          Image:
            "//a.amap.com/jsapi_demos/static/demo-center/icons/dir-marker.png",
          ImageSize: new AMap.Size(135, 40)
        });
        //终点标记
        var endicon = new AMap.Icon({
          size: new AMap.Size(25, 34),
          Image:
            "//a.amap.com/jsapi_demos/static/demo-center/icons/dir-marker.png",
          ImageSize: new AMap.Size(135, 40)
        });
        //经过点标记
        var viaicon = new AMap.Icon({
          size: new AMap.Size(25, 34),
          Image:
            "//a.amap.com/jsapi_demos/static/demo-center/icons/dir-via-marker.png",
          ImageSize: new AMap.Size(135, 40)
        });
        var temstrategy = "";
        if (this.strategy == "最快捷模式") {
          temstrategy = 0;
        } else if (this.strategy == "最经济模式") {
          temstrategy = 1;
        } else if (this.strategy == "最短距离模式") {
          temstrategy = 2;
        } else if (this.strategy == "考虑实时路况") {
          temstrategy = 4;
        }
        var geocoder = new AMap.Geocoder({
            // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
            city: "全国",
            radius: 1000,
            extensions: "all"
          });
        var driving = new AMap.Driving({
            policy: temstrategy,
            map: map
          });
        AMap.plugin(["AMap.Driving", "AMap.Geocoder"], function() {
          driving.search(distArrend2, function(status, result) {
            console.log(status);
            if (status === "complete") {
              //console.log("绘制成功")
              //起点标记
              var marker1 = new AMap.Marker({
                map: map,
                position: datapath[0],
                icon: starticon,
                offset: new AMap.Pixel(-10, -26),
                autoRotation: true,
                label: {
                  offset: new AMap.Pixel(20, 20),
                  content: "起点:" + distArrend[0].keyword[0]
                }
              });
              //终点标记
              var marker2 = new AMap.Marker({
                map: map,
                position: datapath[datapath.length - 1],
                icon: endicon,
                offset: new AMap.Pixel(-10, -26),
                autoRotation: true,
                label: {
                  offset: new AMap.Pixel(20, 20),
                  content:
                    "终点:" +
                    distArrend[0].keyword[distArrend[0].keyword.length - 1]
                }
              });
              map.add([marker1, marker2]);
              //经过点标记
              var mar = [];
              var marker3;
              geocoder.getLocation(distArrend[0].keyword, function(status,result) {
                if (status === "complete" && result.info === "OK") {
                  // result中对应详细地理坐标信息
                  console.log(result);
                  // mar = [];
                  for (var j = 1; j < result.geocodes.length - 1; j++) {
                    mar = [
                      result.geocodes[j].location.lng,
                      result.geocodes[j].location.lat
                    ];
                    marker3 = new AMap.Marker({
                      map: map,
                      position: mar,
                      icon: viaicon,
                      offset: new AMap.Pixel(-10, -26),
                      autoRotation: true,
                      label: {
                        offset: new AMap.Pixel(20, 20),
                        content:
                          "经点" + parseInt(j) + "：" + distArrend[0].keyword[j]
                      }
                    });
                    map.add(marker3);
                  }
                }
              });
            } else {
              console.log("绘制失败！")
            }
          });
        });
        map.setFitView();
        return lnglatpath, distArrend;
      }
    },
    //保存路线
    tosave() {
      console.log(datapath);
      console.log(distArrend[0].keyword);
      var tempath = distArrend[0].keyword.join(";");
      this.tempath1 = distArrend[0].keyword.join(";");
      var temstrategy = "";
      if (this.strategy == "最快捷模式") {
        temstrategy = 0;
      } else if (this.strategy == "最经济模式") {
        temstrategy = 1;
      } else if (this.strategy == "最短距离模式") {
        temstrategy = 2;
      } else if (this.strategy == "考虑实时路况") {
        temstrategy = 4;
      }
      var secc = "";
      console.log(decodeURI(tempath))
      console.log(temstrategy)
      const _this = this;
      axios.post('http://localhost:8282/teminfo/insertTeminfo/'+this.routeid+'&'+distArrend[0].keyword.join(",")+'&'+temstrategy).then(function(resp) {
          console.log(resp);
          if(resp.data ="success"){
            _this.dialogVisible = true;
          }
        });
      for (var i = 0; i < datapath.length; i++) {
        axios.post("http://localhost:8282/position/insertPosition/" +this.routeid +"&" +datapath[i][0] +"&" +datapath[i][1]
          ).then(function(resp) {
            console.log(resp);
            //secc = resp.data;
          });
      }
      
    },

    //跳转回上一页面
    dialog() {
      this.$router.push({ path: "/gooddist" });
    }
  },

  created() {
    const _this = this;
    axios
      .get("http://localhost:8282/route/findRouteByRoutetime")
      .then(function(resp) {
        console.log(resp);
        _this.routeid = resp.data[0].routeid;
      });
    this.carnumber = this.$route.query.carnumber;
    this.caraddr = this.$route.query.caraddr;
    this.goodweight = this.$route.query.goodweight;
    this.goodvolume = this.$route.query.goodvolume;
    this.goodnum = this.$route.query.goodnum;
    this.tableData = this.$route.query.multipleSelection;
    console.log(this.tableData[0].endaddr);
    console.log(this.tableData);
    var pathtem = [];
    for (var i = 0; i < this.tableData.length; i++) {
      pathtem.push(this.tableData[i].startaddr, this.tableData[i].endaddr);
    }
    pathtem.splice(0, 0, this.caraddr);
    console.log(pathtem);
    var newarr = [];
    for (var j = 0; j < pathtem.length; j++) {
      if (!newarr.includes(pathtem[j])) {
        newarr.push(pathtem[j]);
      }
    }
    path = [];
    for (var i = 0; i < newarr.length; i++) {
      path.push({ keyword: newarr[i] });
    }
    console.log(path);
    return path;
  }
};
</script>