<template>
  <div>
    <div style="width:100%;height:40px;background:#fff;">
      <el-button type="primary" @click="fanhui">返回</el-button>
    </div>
    <div id="amap-container" style="width:100%; height: 610px;"></div>
  </div>
</template>
<script>
import AMap from "AMap";
var map;
export default {
  mounted() {
    this.initAMap();
  },
  methods: {
    //返回
    fanhui(){
      history.back();
    },
    //地图初始化
    initAMap: function() {
      map = new AMap.Map("amap-container", {
        resizeEnable: true,
        zoom: 12, // 级别
        center: [108.314722, 22.80389], // 中心点坐标
        viewMode: "3D" //使用3D视图
      });
      //加载插件
      AMap.plugin(["AMap.ToolBar", "AMap.Scale"], function() {
        //地图定位+工具条
        map.addControl(new AMap.ToolBar()); //添加工具条插件
        map.addControl(new AMap.Scale());
      });

      // 多边形轮廓线的节点坐标数组1
      var pathxxt1 = [
        new AMap.LngLat(108.3209, 22.811294),
        new AMap.LngLat(108.324677, 22.819838),
        new AMap.LngLat(108.304078, 22.853378),
        new AMap.LngLat(108.262536, 22.854011),
        new AMap.LngLat(108.253609, 22.846734),
        new AMap.LngLat(108.253266, 22.828699),
        new AMap.LngLat(108.267342, 22.824585),
        new AMap.LngLat(108.281762, 22.811294),
        new AMap.LngLat(108.293778, 22.802116),
        new AMap.LngLat(108.302361, 22.804015),
        new AMap.LngLat(108.309571, 22.810977)
      ];
      var pathxxt2 = [
        new AMap.LngLat(108.2155, 22.824245),
        new AMap.LngLat(108.213784, 22.832472),
        new AMap.LngLat(108.188035, 22.832155),
        new AMap.LngLat(108.175675, 22.823928),
        new AMap.LngLat(108.162285, 22.839749),
        new AMap.LngLat(108.203484, 22.841331),
        new AMap.LngLat(108.215844, 22.858732),
        new AMap.LngLat(108.244683, 22.869172),
        new AMap.LngLat(108.259618, 22.888443),
        new AMap.LngLat(108.285023, 22.898896),
        new AMap.LngLat(108.311114, 22.89507),
        new AMap.LngLat(108.344073, 22.882102),
        new AMap.LngLat(108.334116, 22.870399),
        new AMap.LngLat(108.33652, 22.861857),
        new AMap.LngLat(108.325534, 22.856772),
        new AMap.LngLat(108.304934, 22.852975),
        new AMap.LngLat(108.262706, 22.853608),
        new AMap.LngLat(108.253436, 22.845066),
        new AMap.LngLat(108.253093, 22.82703),
        new AMap.LngLat(108.235755, 22.827346)
      ];
      var pathxn1 = [
        new AMap.LngLat(108.322615, 22.813106),
        new AMap.LngLat(108.325019, 22.819435),
        new AMap.LngLat(108.304419, 22.852975),
        new AMap.LngLat(108.326392, 22.857064),
        new AMap.LngLat(108.336691, 22.862442),
        new AMap.LngLat(108.335661, 22.870351),
        new AMap.LngLat(108.344931, 22.882687),
        new AMap.LngLat(108.369307, 22.87984),
        new AMap.LngLat(108.405699, 22.852002),
        new AMap.LngLat(108.4172, 22.847707),
        new AMap.LngLat(108.4275, 22.83695),
        new AMap.LngLat(108.432993, 22.812584)
      ];
      var pathjn1 = [
        new AMap.LngLat(108.33269, 22.728714),
        new AMap.LngLat(108.319644, 22.745813),
        new AMap.LngLat(108.317326, 22.760852),
        new AMap.LngLat(108.323649, 22.76043),
        new AMap.LngLat(108.328799, 22.769294),
        new AMap.LngLat(108.332404, 22.780532),
        new AMap.LngLat(108.344077, 22.784647),
        new AMap.LngLat(108.346137, 22.789394),
        new AMap.LngLat(108.339957, 22.790819),
        new AMap.LngLat(108.327597, 22.789078),
        new AMap.LngLat(108.321761, 22.795385),
        new AMap.LngLat(108.318327, 22.808044),
        new AMap.LngLat(108.308371, 22.804879),
        new AMap.LngLat(108.300131, 22.799183),
        new AMap.LngLat(108.292235, 22.799318),
        new AMap.LngLat(108.279532, 22.809762),
        new AMap.LngLat(108.272665, 22.803432),
        new AMap.LngLat(108.270949, 22.774628),
        new AMap.LngLat(108.270262, 22.74012),
        new AMap.LngLat(108.304594, 22.736636),
        new AMap.LngLat(108.305281, 22.72302),
        new AMap.LngLat(108.314894, 22.717637)
      ];
      var pathjn2 = [
        new AMap.LngLat(108.279532, 22.809762),
        new AMap.LngLat(108.27421, 22.802143),
        new AMap.LngLat(108.272151, 22.775871),
        new AMap.LngLat(108.270434, 22.73978),
        new AMap.LngLat(108.214472, 22.754344),
        new AMap.LngLat(108.168124, 22.797079),
        new AMap.LngLat(108.167094, 22.816068),
        new AMap.LngLat(108.18632, 22.831257),
        new AMap.LngLat(108.214644, 22.832206),
        new AMap.LngLat(108.216876, 22.819865),
        new AMap.LngLat(108.24314, 22.825561),
        new AMap.LngLat(108.257903, 22.824746),
        new AMap.LngLat(108.269233, 22.818575)
      ];
      var pathlq1 = [
        new AMap.LngLat(108.317125, 22.761491),
        new AMap.LngLat(108.323134, 22.760858),
        new AMap.LngLat(108.324335, 22.766081),
        new AMap.LngLat(108.329485, 22.769405),
        new AMap.LngLat(108.335493, 22.764023),
        new AMap.LngLat(108.34708, 22.765211),
        new AMap.LngLat(108.37755, 22.782621),
        new AMap.LngLat(108.380984, 22.781513),
        new AMap.LngLat(108.398665, 22.768851),
        new AMap.LngLat(108.403986, 22.764894),
        new AMap.LngLat(108.417204, 22.753948),
        new AMap.LngLat(108.428019, 22.762179),
        new AMap.LngLat(108.427504, 22.77007),
        new AMap.LngLat(108.430594, 22.779725),
        new AMap.LngLat(108.435744, 22.784947),
        new AMap.LngLat(108.436087, 22.756219),
        new AMap.LngLat(108.424929, 22.70975),
        new AMap.LngLat(108.383044, 22.717351),
        new AMap.LngLat(108.343905, 22.713868),
        new AMap.LngLat(108.327769, 22.709997),
        new AMap.LngLat(108.314036, 22.718231),
        new AMap.LngLat(108.333262, 22.728998),
        new AMap.LngLat(108.324164, 22.740849),
        new AMap.LngLat(108.318328, 22.746999)
      ];
      var pathqx1 = [
        new AMap.LngLat(108.3197, 22.811004),
        new AMap.LngLat(108.323992, 22.803859),
        new AMap.LngLat(108.327596, 22.789775),
        new AMap.LngLat(108.335665, 22.793098),
        new AMap.LngLat(108.344763, 22.792624),
        new AMap.LngLat(108.348024, 22.789221),
        new AMap.LngLat(108.345106, 22.782234),
        new AMap.LngLat(108.331545, 22.776061),
        new AMap.LngLat(108.333604, 22.768305),
        new AMap.LngLat(108.343732, 22.766406),
        new AMap.LngLat(108.36828, 22.782234),
        new AMap.LngLat(108.37961, 22.7835),
        new AMap.LngLat(108.382185, 22.781917),
        new AMap.LngLat(108.388364, 22.777169),
        new AMap.LngLat(108.401411, 22.770996),
        new AMap.LngLat(108.406389, 22.765773),
        new AMap.LngLat(108.412912, 22.758016),
        new AMap.LngLat(108.424757, 22.761657),
        new AMap.LngLat(108.426302, 22.775586),
        new AMap.LngLat(108.432653, 22.785715),
        new AMap.LngLat(108.435615, 22.786632),
        new AMap.LngLat(108.438318, 22.813574),
        new AMap.LngLat(108.378838, 22.812941),
        new AMap.LngLat(108.322225, 22.812913)
      ];

      var polygonxxt1 = new AMap.Polygon({
        path: pathxxt1,
        fillColor: "#1791fc", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });
      var polygonxxt2 = new AMap.Polygon({
        path: pathxxt2,
        fillColor: "#9acd32", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });
      var polygonxn1 = new AMap.Polygon({
        path: pathxn1,
        fillColor: "#ffff00", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });
      var polygonjn1 = new AMap.Polygon({
        path: pathjn1,
        fillColor: "#f08080", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });
      var polygonjn2 = new AMap.Polygon({
        path: pathjn2,
        fillColor: "#ba2be2", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });
      var polygonlq1 = new AMap.Polygon({
        path: pathlq1,
        fillColor: "#1791fc", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });
      var polygonqx1 = new AMap.Polygon({
        path: pathqx1,
        fillColor: "#f0ffff", // 多边形填充颜色
        fillOpacity: 0.35, //填充透明度
        strokeOpacity: 0.4, //线透明度
        borderWeight: 1, // 线条宽度，默认为 1
        strokeColor: "red" // 线条颜色
      });

      map.add(polygonxxt1);
      map.add(polygonxxt2);
      map.add(polygonxn1);
      map.add(polygonjn1);
      map.add(polygonjn2);
      map.add(polygonlq1);
      map.add(polygonqx1);

      // 创建纯文本标记
      var textxxt1 = new AMap.Text({
        text: "西乡塘西大区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.289189, 22.831068]
      });
      // 创建纯文本标记
      var textxxt2 = new AMap.Text({
        text: "西乡塘二区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.274367, 22.876247]
      });
      // 创建纯文本标记
      var textxn1 = new AMap.Text({
        text: "兴宁一区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.369087, 22.843851]
      });
      // 创建纯文本标记
      var textjn1 = new AMap.Text({
        text: "江南一区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.294094, 22.774986]
      });
      // 创建纯文本标记
      var textjn2 = new AMap.Text({
        text: "江南二区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.2145, 22.793539]
      });
      // 创建纯文本标记
      var textlq1 = new AMap.Text({
        text: "良庆一区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.365992, 22.732994]
      });
      // 创建纯文本标记
      var textqx1 = new AMap.Text({
        text: "青秀一区",
        anchor: "center", // 设置文本标记锚点
        draggable: true,
        cursor: "pointer",
        angle: 10,
        style: {
          padding: ".35rem 0.75rem",
          "margin-bottom": ".75rem",
          "border-radius": ".25rem",
          "background-color": "white",
          width: "4rem",
          "border-width": 0,
          "box-shadow": "0 2px 6px 0 rgba(114, 124, 245, .5)",
          "text-align": "center",
          "font-size": "10px",
          color: "blue"
        },
        position: [108.402632, 22.79092]
      });

      textxxt1.setMap(map);
      textxxt2.setMap(map);
      textxn1.setMap(map);
      textjn1.setMap(map);
      textjn2.setMap(map);
      textlq1.setMap(map);
      textqx1.setMap(map);

      //起点标记
        var starticon = new AMap.Icon({
          size: new AMap.Size(25, 34),
          Image:
            "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png",
          ImageSize: new AMap.Size(135, 40)
        });
        //终点标记
        var endicon = new AMap.Icon({
          size: new AMap.Size(25, 34),
          Image:
            "//a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-red.png",
          ImageSize: new AMap.Size(135, 40)
        });
        var startaddr = [];
        var endaddr = [];
        var mar = [];
        var marker1,marker2;
        const _this = this;
        axios.get('http://localhost:8282/good/findGoodByGoodstate').then(function(resp) {
            console.log(resp.data);
            for(var i=0;i<resp.data.length;i++){
              startaddr.push(resp.data[i].startaddr)
              endaddr.push(resp.data[i].endaddr)
            }
            console.log(startaddr,endaddr)
            AMap.plugin(["AMap.Geocoder"], function() {
              var geocoder = new AMap.Geocoder({
                // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                city: "全国",
                radius: 1000,
                extensions: "all"
              });
              geocoder.getLocation(startaddr, function(status,result) {
                console.log(result.geocodes)
                for (var j = 0; j < result.geocodes.length; j++) {
                    mar = [
                      result.geocodes[j].location.lng,
                      result.geocodes[j].location.lat
                    ];
                    marker1 = new AMap.Marker({
                      map: map,
                      position: mar,
                     // icon: starticon,
                      offset: new AMap.Pixel(-10, -26),
                      autoRotation: true,
                    });
                    map.add(marker1);
                }
              })
              geocoder.getLocation(endaddr, function(status,result) {
                console.log(result.geocodes)
                for (var j = 0; j < result.geocodes.length; j++) {
                    mar = [
                      result.geocodes[j].location.lng,
                      result.geocodes[j].location.lat
                    ];
                    marker2 = new AMap.Marker({
                      map: map,
                      position: mar,
                      icon: endicon,
                      offset: new AMap.Pixel(-10, -26),
                      autoRotation: true,
                    });
                    map.add(marker2);
                }
              })
            })
         });     
    }
  },

  created(){

  }
};
</script>