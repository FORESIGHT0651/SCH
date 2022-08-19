<template>
  <div>
      <div id="amap-container" style="width:100%; height: 650px;"></div>
  </div>
</template>
<script>
import AMap from "AMap";
export default {
  mounted() {
    this.initAMap();
  },

  methods: {
    //地图初始化
    initAMap: function() {
      var map = new AMap.Map("amap-container", {
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
  }
}
</script>