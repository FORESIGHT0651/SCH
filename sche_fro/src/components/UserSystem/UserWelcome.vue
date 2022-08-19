<template>
  <div>
    <div style="float:left;width:23%;height:180px;margin-bottom:20px;background:#fff;">
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
          <i class="el-icon-s-flag"></i> 5 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:23%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
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
          <i class="el-icon-s-order"></i> 23 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:23%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
    >
      <el-card
        shadow="hover"
        style="margin-left:10px;width:90%;height:150px;margin-top:15px;background:#95caca;"
      >
        <span style="color:#fff;font-size:18px">
          <i class="el-icon-s-promotion"></i>在送订单数
        </span>
        <br />
        <br />
        <span style="color:#fff;font-size:30px;font-weight:bold">
          <i class="el-icon-s-order"></i> 2 个
        </span>
      </el-card>
    </div>
    <div
      style="float:left;width:23%;height:180px;margin-left:2%;margin-bottom:20px;background:#fff;dislpay:inline"
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
          <i class="el-icon-s-order"></i> 0 个
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
          text: "个人配送区域订单量",
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
              { value: 23, name: "西乡塘西大区" },
              { value: 1, name: "西乡塘二区" },
              { value: 23, name: "兴宁一区" },
              { value: 1, name: "江南一区" },
              { value: 2, name: "江南二区" },
              { value: 3, name: "青秀一区" },
              { value: 3, name: "良庆一区" }
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
            text: "本周个人配送统计"
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
              data: [3, 5, 4, 5, 4, 1, 2]
            },
            {
              name: "已送订单量",
              type: "line",
              data: [2, 2, 4, 4, 5, 4, 1]
            },
            {
              name: "未送订单量",
              type: "line",
              data: [1, 3, 3, 4, 3, 0, 1]
            },
            {
              name: "运单总量",
              type: "line",
              data: [5, 4, 2, 7, 6, 5, 7]
            }
          ]
        });
         window.onresize = myChartline.resize; //自适应浏览器窗口的大小
      }, 1000);
    }
  }
};
</script>