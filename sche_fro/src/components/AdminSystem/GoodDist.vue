<template>
  <div>
    <el-card>
      
      已选订单总重量(公斤)：
      <el-input class="goodweight" style="width: 120px;" v-model="goodweight" readonly></el-input>&#8195;&#8195;
      已选订单总体积(m³)：
      <el-input
        id="goodvolume"
        name="goodvolume"
        style="width: 120px;"
        v-model="goodvolume"
        readonly
      ></el-input>&#8195;&#8195;
      已选订单数(个)：
      <el-input style="width: 120px;" v-model="goodnum" readonly></el-input>&#8195;&#8195;
      总件数(件)：
      <el-input style="width: 120px;" v-model="goodtotal" readonly></el-input>&#8195;&#8195;
      <el-button type="primary" icon="el-icon-truck" @click="testgoodweight">检查车货匹配</el-button>
      <br />
      <br />
      <el-input v-model="distcaraddr" style="display:none"></el-input>
      车辆编号:
      <el-input style="width: 120px;" v-model="distcarid" readonly></el-input>&#8195;&#8195;
      车牌号：
      <el-input style="width: 120px;" v-model="distcarnumber" readonly></el-input>&#8195;&#8195;
      实际可用载重(公斤)：
      <el-input style="width: 120px;" v-model="weight" readonly></el-input>&#8195;&#8195;
      实际可用容积(m³)：
      <el-input style="width: 120px;" v-model="volume" readonly></el-input>&#8195;&#8195;
      <br />
      <br />起送区域：
      <el-select v-model="startlbsfind" clearable placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>&#8195;&#8195;
      终点区域：
      <el-select v-model="endlbsfind" clearable placeholder="请选择">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>&#8195;&#8195;
      <el-button type="primary" icon="el-icon-search" @click="findgood()">查 询</el-button>
      <el-button type="warning" icon="el-icon-check" @click="todist">确认分配</el-button>
      <br />
      <el-link type="success" href="/#/lbs">查看订单配送区域</el-link>
      <br />
      <!--表格订单-->
      <el-table
        ref="multipleTable"
        :data="tableData1"
        height="300"
        border
        tooltip-effect="dark"
        @selection-change="handleSelectionChange"
        @row-click="choosegood"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="goodid" label="订单编号"></el-table-column>
        <el-table-column prop="goodweight" label="重量(公斤)"></el-table-column>
        <el-table-column prop="goodvolume" label="体积(m³)"></el-table-column>
        <el-table-column prop="goodnum" label="件数"></el-table-column>
        <el-table-column prop="startaddr" label="出发地"></el-table-column>
        <el-table-column prop="endaddr" label="目的地"></el-table-column>
        <el-table-column prop="startlbs" label="起始区域"></el-table-column>
        <el-table-column prop="endlbs" label="终点区域"></el-table-column>
        <el-table-column prop="goodtime" label="限时时间"></el-table-column>
        <el-table-column prop="goodstate" label="状态"></el-table-column>
      </el-table>
      <br />
      <br />
      <!--搜索-->
      <el-input style="width:20%" placeholder="请输入车牌号" v-model="findcarnumber" clearable></el-input>&#8195;&#8195;
      <el-select v-model="carlbsfind" clearable placeholder="请选择车辆所在区域">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>&#8195;&#8195;
      <el-button type="primary" icon="el-icon-search" @click="findcar()">查 询</el-button>&#8195;&#8195;
      <el-button type="primary" @click="setCurrent()">取消选择</el-button>

      <!--表格车辆-->
      <el-table
        ref="singleTable"
        :data="tableData2"
        height="250"
        border
        highlight-current-row
        @current-change="handleCurrentChange"
        @row-click="choosecar"
      >
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="carid" label="车辆编号"></el-table-column>
        <el-table-column prop="carnumber" label="车牌号"></el-table-column>
        <el-table-column prop="username" label="司机"></el-table-column>
        <el-table-column prop="cartype" label="车型"></el-table-column>
        <el-table-column prop="carvehicle" label="车载重(吨)"></el-table-column>
        <el-table-column prop="carvolume" label="容积(m³)"></el-table-column>
        <el-table-column prop="carrate" label="装载率(X100%)" width="130px"></el-table-column>
        <el-table-column prop="caraddr" label="当前位置"></el-table-column>
        <el-table-column prop="carlbs" label="所在区域"></el-table-column>
        <el-table-column prop="carway" label="在送运单数"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData1: [
        {
          goodid: "111",
          goodweight: "15",
          goodvolume: "1",
          goodnum: "23"
        },
        {
          goodid: "111",
          goodweight: "13",
          goodvolume: "0.5",
          goodnum: "23"
        }
      ],
      tableData2: [],
      distcaraddr:"",
      distcarnumber: "",
      distcarid: "",
      weight: "0",
      volume: "0",
      goodweight: "0",
      goodvolume: "0",
      goodnum: "0",
      goodtotal: "0",
      startlbsfind: "",
      endlbsfind: "",
      carlbsfind: "",
      findcarnumber:"",
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
      ],
      multipleSelection: []
    };
  },

  methods: {
    //按区域查询订单
    findgood(){
      if(this.startlbsfind != '' && this.endlbsfind ==''){
        const _this = this;
        axios.get('http://localhost:8282/good/findGoodByStartlbs/'+this.startlbsfind).then(function(resp) {
          console.log(resp);
          _this.tableData1 = resp.data;
        });
      }else if(this.startlbsfind == '' && this.endlbsfind !=''){
        const _this = this;
        axios.get('http://localhost:8282/good/findGoodByEndlbs/'+this.endlbsfind).then(function(resp) {
          console.log(resp);
          _this.tableData1 = resp.data;
        });
      }else if(this.startlbsfind != '' && this.endlbsfind !=''){
        const _this = this;
        axios.get('http://localhost:8282/good/findGoodByStartlbsAndEndlbs/'+this.startlbsfind+'&'+this.endlbsfind)
          .then(function(resp) {
          console.log(resp);
          _this.tableData1 = resp.data;
        });
      }else if(this.startlbsfind == '' && this.endlbsfind ==''){
        const _this = this;
        axios.get('http://localhost:8282/good/findGoodByGoodstate').then(function(resp) {
          console.log(resp);
          _this.tableData1 = resp.data;
        });
      }
    },

    //按车牌号、区域查询车辆
    findcar(){
      if(this.findcarnumber != '' && this.carlbsfind == ''){
        const _this = this;
        axios.get('http://localhost:8282/car/findCarByCarnumber/'+this.findcarnumber).then(function(resp) {
          console.log(resp);
          _this.tableData2 = resp.data;
        });
      }else if(this.findcarnumber == '' && this.carlbsfind != ''){
        const _this = this;
        axios.get('http://localhost:8282/car/findCarByCarlbs/'+this.carlbsfind).then(function(resp) {
          console.log(resp);
          _this.tableData2 = resp.data;
        });
      }else if(this.findcarnumber != '' && this.carlbsfind != ''){
        const _this = this;
        axios.get('http://localhost:8282/car/findCarByCarnumberAndCarlbs/'+this.findcarnumber+'&'+this.carlbsfind).then(function(resp) {
          console.log(resp);
          _this.tableData2 = resp.data;
        });
      }else if(this.findcarnumber == '' && this.carlbsfind == ''){
        const _this = this;
        axios.get('http://localhost:8282/car/findCarByCarway').then(function(resp) {
          console.log(resp);
          _this.tableData2 = resp.data;
        });
      }
    },

    //多选
    handleSelectionChange(val) {
      const _this = this;
      var sumweight = 0;
      var sumvolume = 0;
      var countnum = 0;
      var sumtotal = 0;
      _this.multipleSelection.length = 0;
      _this.multipleSelection = val;
      console.log(_this.multipleSelection);
      for (var i in _this.multipleSelection) {
        sumweight +=
          _this.multipleSelection[i].goodweight *
          _this.multipleSelection[i].goodnum;
        sumvolume +=
          _this.multipleSelection[i].goodvolume *
          _this.multipleSelection[i].goodnum;
        sumtotal =
          parseInt(sumtotal) + parseInt(_this.multipleSelection[i].goodnum);
        countnum = ++i;
      }
      this.goodweight = sumweight;
      this.goodvolume = sumvolume;
      this.goodnum = countnum;
      this.goodtotal = sumtotal;
    },
    choosegood(row) {
      this.$refs.multipleTable.toggleRowSelection(row);
      // console.log(row.goodid);
    },

    //单选
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.distcarid = "";
      this.distcarnumber = "";
      this.weight = 0;
      this.volume = 0;
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    choosecar(row) {
      this.$refs.singleTable.setCurrentRow(row);
      this.distcaraddr = row.caraddr;
      this.distcarid = row.carid;
      this.distcarnumber = row.carnumber;
      this.weight = row.carvehicle * 1000 * row.carrate;
      this.volume = row.carvolume * row.carrate;
    },

    //测试车货匹配
    testgoodweight() {
      if (this.goodweight <= this.weight && this.goodvolume <= this.volume) {
        this.$message({
          type: "success",
          message: "车货匹配合理！"
        });
      } else {
        this.$message({
          type: "error",
          message: "车货匹配不合理，请重新分配！"
        });
      }
    },

    //分配
    todist() {
     console.log(this.multipleSelection)
      if (this.goodweight <= this.weight && this.goodvolume <= this.volume && this.goodweight!=0) {
        this.$confirm(
          "此操作将为车辆（" +
            this.distcarnumber +
            "）分配订单并跳转至下一步骤, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        )
          .then(() => {
            const _this = this;
            axios.post('http://localhost:8282/route/insertRoute/'+this.distcarid+'&'+this.goodweight
            +'&'+this.goodvolume+'&'+this.goodnum+'&'+this.goodtotal).then(function(resp) {
                   console.log(resp.data);
                   if(resp.data == "success"){
                     axios.get('http://localhost:8282/route/findRouteByRoutetime').then(function(res) {
                      console.log(res);
                      if(res.status == 200){
                        for(var i=0;i<_this.multipleSelection.length;i++){
                          axios.put('http://localhost:8282/good/updateGoodRouteid/'+res.data[0].routeid+'&'
                           +_this.multipleSelection[i].goodid).then(function(re) {
                            console.log(re);
                          });
                        }
                         axios.put('http://localhost:8282/car/updateCarwayToOne/'+_this.distcarid).then(function(resp1) {
                            console.log(resp1);
                          });
                         _this.$router.push({
                            path: "/goodrouteplan",
                            query: {
                              caraddr: _this.distcaraddr,
                              carnumber: _this.distcarnumber,
                              goodweight: _this.goodweight,
                              goodvolume: _this.goodvolume,
                              goodnum: _this.goodnum,
                              startaddr: _this.startaddr,
                              multipleSelection: _this.multipleSelection
                            }
                         });
                        _this.$message({
                          type: "success",
                          message: "分配成功，请安排路线！"
                        }); 
                      }
                    });
                   }else{
                     _this.$message({
                         type: "error",
                         message: "分配失败！"
                      });
                   }   
              });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消分配"
            });
          });
      } else {
        this.$message({
          type: "error",
          message: "车货匹配不合理，请重新分配！"
        });
      }
    },
  },

  created(){
    const _this = this;
    axios.get('http://localhost:8282/good/findGoodByGoodstate').then(function(resp) {
        console.log(resp);
        _this.tableData1 = resp.data;
      });
    axios.get('http://localhost:8282/car/findCarByCarway').then(function(resp) {
        console.log(resp);
        _this.tableData2 = resp.data;
    });
  }
};
</script>