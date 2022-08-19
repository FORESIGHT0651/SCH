<template>
  <div>
    <div id="amap-container" style="width:100%; height: 650px;"></div>
    <div style="width:25%;height:150px;position:absolute; right:60px;top:100px;">
      <el-card shadow="always">
        路线编号：<el-input v-model="gid" Readonly style="width:270px" :disabled="true"></el-input>        
        <br />
        <br />
        <template>
          <div class="block">
            <span class="demonstration">时速(km/h)</span>
            <el-slider
              v-model="kmh"
              :step="200"
              max="2000"
              @change="changekmh"
              show-stops
              show-input
            ></el-slider>
          </div>
        </template>
        <br />
        <el-row>
          <el-button type="primary" size="mini" plain @click="startAnimation">开始</el-button>&#8195;
          <el-button type="primary" size="mini" plain @click="pauseAnimation">暂停</el-button>&#8195;
          <el-button type="primary" size="mini" plain @click="resumeAnimation">继续</el-button>&#8195;
          <el-button type="primary" size="mini" plain @click="stopAnimation">停止</el-button>
        </el-row>
      </el-card>
    </div>
  </div>
</template>
<script>
import AMap from "AMap";
// var lineArr = [
//   [108.288254, 22.832401],
//   [108.290582, 22.83162],
//   [108.291838, 22.831215],
//   [108.292299, 22.831091],
//   [108.293039, 22.831536],
//   [108.29386, 22.831907],
//   [108.294895, 22.832372],
//   [108.295673, 22.832728],
//   [108.297063, 22.833341],
//   [108.298076, 22.833806],
//   [108.299868, 22.834602],
//   [108.301376, 22.835294],
//   [108.302969, 22.835996],
//   [108.30475, 22.836821],
//   [108.307105, 22.83783],
//   [108.309551, 22.838809],
//   [108.311284, 22.839481],
//   [108.313, 22.840277],
//   [108.314856, 22.841296]
// ];
// var newlineArr = [
//   [108.288254, 22.832401],
//   [108.290582, 22.83162],
//   [108.291838, 22.831215],
//   [108.292299, 22.831091],
//   [108.293039, 22.831536],
//   [108.29386, 22.831907],
//   [108.294895, 22.832372],
//   [108.295673, 22.832728],
//   [108.297063, 22.833341],
//   [108.298076, 22.833806],
//   [108.299868, 22.834602],
//   [108.301376, 22.835294],
//   [108.302969, 22.835996],
//   [108.30475, 22.836821],
//   [108.307105, 22.83783],
//   [108.309551, 22.838809],
//   [108.311284, 22.839481],
//   [108.313, 22.840277],
//   [108.314856, 22.841296]
// ];
var lng = [];
var lat = [];
var lnglat=[[]];
var newlnglat=[[]];
var lineArr = [[]];
var marker;
var havePassedLine = [];
var NopassedLine = [];
var passedLineLength;
var map;
export default {
  data() {
    return {
     // lineArr:[[]],
      marker: {},
      gid: "1",
      map: "",
      kmh: 1000,
      startdate: "",
      enddate: ""
    };
  },

  mounted() {
    this.init();
  },

  methods: {
     
    //地图初始化
    init() {
        //地图初始化
        map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 13, // 级别
        viewMode: "3D", //使用3D视图
        center: [108.413388, 22.842119] // 中心点坐标
      });
      //工具条
      AMap.plugin(["AMap.ToolBar", "AMap.Scale"], function() {
        map.addControl(new AMap.ToolBar()); //添加工具条插件
        map.addControl(new AMap.Scale());
      });

      //获取数据
      const _this = this;
      axios.get("http://localhost:8282/position/findPositionByRouteid/"+this.$route.query.routeid).then(function(resp){
        console.log(resp)
        for(var i in resp.data){        
          _this.$set(lineArr,i,resp.data[i]); 
        }
        for(var j in lineArr){
          _this.$set(lng,j,lineArr[j].longitude)
          _this.$set(lat,j,lineArr[j].latitude)
        }
        console.log(lng)
        console.log(lat)
        lnglat = lng.map((item,idx)=>[item,lat[idx]])
        newlnglat = lng.map((item,idx)=>[item,lat[idx]])
        console.log(lnglat[0])
       //点标记
      marker = new AMap.Marker({
        map: map,
        position: lnglat[0],
        icon: "https://webapi.amap.com/images/car.png",
        offset: new AMap.Pixel(-26, -13),
        autoRotation: true,
        angle:20,
      });
      map.add(marker);
      
      // 绘制轨迹
      var polyline = new AMap.Polyline({
        map: map,
        path: lnglat,
        showDir: true,
        strokeColor: "#28F", //线颜色
        // strokeOpacity: 1,     //线透明度
        strokeWeight: 6, //线宽
        strokeStyle: "solid" //线样式
      });
      map.setFitView();
      var passedPolyline = new AMap.Polyline({
        map: map,
        // path: lineArr,
        strokeColor: "#AF5", //线颜色
        strokeOpacity: 1, //线透明度
        strokeWeight: 6 //线宽
        // strokeStyle: "solid"  //线样式
      });
       marker.on("moving", function(e) {
        passedLineLength = e.passedPath.length;
        havePassedLine = e.passedPath;
        passedPolyline.setPath(e.passedPath);
      });
      });
      
    },

    //控制播放按钮
    startAnimation() {
      marker.stopMove();
      if (lnglat.length < newlnglat.length) {
        lnglat = newlnglat;
        this.init();
        marker.moveAlong(lnglat, this.kmh);
      } else {
        marker.moveAlong(lnglat, this.kmh);
      }
    },
    pauseAnimation() {
      marker.pauseMove();
    },
    resumeAnimation() {
      marker.resumeMove();
    },
    stopAnimation() {
      marker.stopMove();
    },

    changekmh() {
      //点击将已经运动的点颜色改成和之前一样
      var polyline = new AMap.Polyline({
        map: map,
        path: havePassedLine,
        showDir: true,
        strokeColor: "#AF5", //线颜色
        // strokeOpacity: 1,     //线透明度
        strokeWeight: 6, //线宽
        strokeStyle: "solid" //线样式
      });
      //截取未运动的点
      if (passedLineLength >= 2) {
        NopassedLine = lnglat.slice(passedLineLength - 2);
      }
      //将未运动的点变成新的点
      lnglat = NopassedLine;
      //绘制轨迹
      var newpolyline = new AMap.Polyline({
        map: map,
        path: NopassedLine,
        showDir: true,
        strokeColor: "#28F", //线颜色
        // strokeOpacity: 1,     //线透明度
        strokeWeight: 6, //线宽
        strokeStyle: "solid" //线样式
      });
      //绘制运动过了的轨迹
      var newpassedPolyline = new AMap.Polyline({
        map: map,
        // path: lineArr,
        strokeColor: "#AF5", //线颜色
        strokeOpacity: 1, //线透明度
        strokeWeight: 6 //线宽
        // strokeStyle: "solid"  //线样式
      });
      marker.on("moving", function(e) {
        passedLineLength = e.passedPath.length;
        havePassedLine = e.passedPath;
        newpassedPolyline.setPath(e.passedPath);
      });
      map.add(marker);
     // map.setFitView();
      marker.moveAlong(lnglat, this.kmh);
    }
  },

  created() {   
      this.gid = this.$route.query.routeid;
      
  }
};
</script>
<style lang="less" scoped>
</style>