<template>
  <div>
    <div>
      <!-- <span id="res" class="res" style="display:none" value></span> -->
      <!-- <el-input  v-model="city" type="hidden"></el-input> -->
    </div>
    <div id="amap-container" style="width:100%; height: 650px;"></div>

    <div
      id="search-car"
      style="width:22%;height:250px;position:absolute;right:40px;top:100px;background:#fff;"
    >
      <!--卡片视图-->
      <el-card style="width:300">
        <!--搜索-->
        <el-row>
          <el-col :span="70">
            <el-input placeholder="请输入车牌号" v-model="findinput" clearable>
              <el-button slot="append" icon="el-icon-search" @click="find()"></el-button>
            </el-input>
          </el-col>
        </el-row>
        <!--表格-->
        <el-table
          ref="tableDatacar"
          :data="tableData"
          height="220"
          highlight-current-row
          @row-click="choose"
        >
          <el-table-column prop="carid" label="编号" width="70"></el-table-column>
          <el-table-column prop="carnumber" label="车牌号"></el-table-column>
          <el-table-column prop="carway" label="在送运单"></el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import AMap from "AMap";
var wendu;
var cartime;
var sudu;
export default {
  city: "",
  name: "AMapPolygon",
  props: {
    height: {
      type: String,
      default: "500px"
    }
  },
  watch: {},

  data() {
    return {
      findinput: "",
      map: null,
      label: {
        content: "aaa",
        offset: [108.29723, 22.80649]
      },
      tableData: [
        {
          carid: "111",
          carnumber: "桂A-56292",
          carstate: "配送中"
        }
      ]
    };
  },

  created() {
    //显示所有车辆
    const _this = this;
    axios
      .get("http://localhost:8282/car/findCarOrderByCarway")
      .then(function(resp) {
        console.log(resp);
        _this.tableData = resp.data;
      });
  },

  mounted() {
    this.initAMap();
    this.choose(row);
  },

  methods: {
    //地图初始化
    initAMap: function() {
      let map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 12, // 级别
        center: [108.413388, 22.842119], // 中心点坐标
        viewMode: "3D" //使用3D视图
      });
      //地图定位+工具条
      AMap.plugin(
        [
          "AMap.ToolBar",
          "AMap.Scale",
          "AMap.Geolocation",
          "AMap.Autocomplete",
          "AMap.PlaceSearch",
          "AMap.Geocoder"
        ],
        function() {
          var getlocation = new AMap.Geolocation({
            timeout: 6000, //设置定位超时时间6s
            GeoLocationFirst: false, //是否使用高精度定位，默认是true
            maximumAge: 0 //定位结果缓存0毫秒，默认0
          });
          map.addControl(new AMap.ToolBar()); //添加工具条插件
          map.addControl(new AMap.Scale());
          map.addControl(getlocation); //把定位插件加入地图实例
          getlocation.getCurrentPosition(); //获取当前位置
          AMap.event.addListener(getlocation, "complete", onComplete);
          AMap.event.addListener(getlocation, "error", onError);
          function onComplete(data) {
            console.log("成功:", data);
          }
          function onError(data) {
            console.log("失败:", data);
          }
        }
      );
    },

    //查询车牌号
    find() {
      //console.log(this.findinput)
      const _this = this;
      if (this.findinput != "") {
        axios
          .get(
            "http://localhost:8282/car/findCarByCarnumberOrder/" +
              this.findinput
          )
          .then(function(resp) {
            console.log(resp);
            _this.tableData = resp.data;
          });
      } else {
        const _this = this;
        axios
          .get("http://localhost:8282/car/findCarOrderByCarway")
          .then(function(resp) {
            console.log(resp);
            _this.tableData = resp.data;
          });
      }
    },

    choose(row) {
      wendu = '+2 ℃',
      cartime = '2020-5-21 12:02:12'
      sudu = '0 km/h'
      //地图初始化
      let map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 12, // 级别
        //center: [108.413731, 22.842499], // 中心点坐标
        viewMode: "3D" //使用3D视图
      });
      //地图工具条
      AMap.plugin(["AMap.ToolBar", "AMap.Scale"], function() {
        var getlocation = new AMap.Geolocation({
          timeout: 6000, //设置定位超时时间6s
          GeoLocationFirst: false, //是否使用高精度定位，默认是true
          maximumAge: 0 //定位结果缓存0毫秒，默认0
        });
        map.addControl(new AMap.ToolBar()); //添加工具条插件
        map.addControl(new AMap.Scale());
      });
      //获取当前位置
      AMap.plugin(
        ["AMap.Autocomplete", "AMap.PlaceSearch", "AMap.Geocoder"],
        function regeocoder() {
          var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: "all"
          });
          //获取数据
          var lnglatXY = [];
          const _this = this;
          axios.get("http://localhost:8282/car/findCarByCarnumberOrder/" + row.carnumber
            ).then(function(resp) {
              console.log(resp);
              geocoder.getLocation(resp.data[0].caraddr, function(status,result) {
                if (status === "complete" && result.info === "OK") {
                  console.log(result);
                  lnglatXY.push(result.geocodes[0].location.lng,result.geocodes[0].location.lat);
                  console.log(lnglatXY)

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
                      row.carnumber +
                      "<br/>当前位置：" +
                      result.geocodes[0].formattedAddress +
                      "<br/>当前区域：" +
                      row.carlbs +
                      "<br/>当前车辆温度：" +
                      wendu +
                      "<br/>采样时间：" +
                      cartime + 
                      "<br/>当前速度：" +
                      sudu
                      +"</div>",
                    offset: new AMap.Pixel(12, -30)
                  });
                  map.setFitView();
                  map.add(marker);
                  infoWindow.open(map, marker.getPosition());
                }
              });
            });
        }
      );
      // function geocoder_CallBack(data) {
      //   var address = data.regeocode.formattedAddress;
      //   document.getElementById("res").innerHTML = address;
      // }
    }
  }
};
</script>