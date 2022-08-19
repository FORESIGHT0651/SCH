<template>
  <div>
    <div style="float:left;width:15%;height:180px;margin-bottom:20px;background:#fff;">
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-star-on"></i>当月总运单完成数
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-s-flag"></i> 65 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:15%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
    >
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-s-claim"></i>当月已送订单数
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-s-order"></i> 232 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:15%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
    >
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-s-promotion"></i>当月在送订单数
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-s-order"></i> 108 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:15%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
    >
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-warning"></i>当月未送订单数
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-s-order"></i> 13 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:15%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
    >
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-bangzhu"></i> 在送车辆
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-truck"></i> 12 辆
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:15%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
    >
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-box"></i> 无任务车辆
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-truck"></i> 5 辆
        </span>
      </el-card>
    </div>
    <div id="mainpie" class="mainpie" style="float:left;width:30%;height:450px;background:#fff;"></div>
    <div id="mainline" class="mainline" style="float:right;width:68%;height:450px;background:#fff;"></div>
  </div>
</template>
<script>
const echarts = require("echarts/lib/echarts");
require("echarts/lib/chart/pie");
require("echarts/lib/chart/line");
require("echarts/lib/component/tooltip");
require("echarts/lib/component/legend");
require("echarts/lib/component/title");
export default {
  mounted() {
    this.initData();
    this.initData2();
  },
  methods: {
    initData() {
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(document.getElementById("mainpie"));

      myChart.setOption({
        title: {
          text: "区域订单量",
          subtext: "SCJ统计",
          left: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: "left",
          data: [
            "西乡塘西大区",
            "西乡塘二区",
            "兴宁一区",
            "江南一区",
            "江南二区",
            "青秀一区",
            "良庆一区"
          ]
        },
        series: [
          {
            name: "订单数及比例",
            type: "pie",
            radius: "45%",
            center: ["50%", "65%"],
            data: [
              { value: 963, name: "西乡塘西大区" },
              { value: 110, name: "西乡塘二区" },
              { value: 634, name: "兴宁一区" },
              { value: 135, name: "江南一区" },
              { value: 221, name: "江南二区" },
              { value: 123, name: "青秀一区" },
              { value: 342, name: "良庆一区" }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      });

      window.onresize = myChart.resize; //自适应浏览器窗口的大小
    },
    async initData2() {
      const myChartline = echarts.init(document.getElementById("mainline"));
      setTimeout(() => {
        myChartline.setOption({
          title: {
            text: "本周配送统计"
          },
          tooltip: {
            trigger: "axis"
          },
          legend: {
            data: ["新增订单量", "已送订单量", "未送订单量", "运单总量"]
          },
          xAxis: {
            type: "category",
            data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
          },
          yAxis: {
            type: "value"
          },
          series: [
            {
              name: "新增订单量",
              type: "line",
              data: [90, 68, 85, 34, 34, 90, 45]
            },
            {
              name: "已送订单量",
              type: "line",
              data: [85, 60, 81, 34, 50, 93, 50]
            },
            {
              name: "未送订单量",
              type: "line",
              data: [5, 15, 19, 19, 13, 10, 5]
            },
            {
              name: "运单总量",
              type: "line",
              data: [30, 20, 41, 12, 24, 35, 25]
            }
          ]
        });
         window.onresize = myChartline.resize; //自适应浏览器窗口的大小
      }, 1000);
    }
  }
};
</script>