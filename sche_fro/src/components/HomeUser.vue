<template>
  <el-container class="home-container">
    <!--侧边栏-->
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="toggle-button" @click="toggleCollapse">
        <i v-if="openside" class="el-icon-s-fold" @click="opensideinfo"></i>
        <i v-else class="el-icon-s-unfold" @click="closesideinfo"></i>
      </div>
      <!--菜单-->
      <el-menu
        class="el-menu-vertical-demo"
        background-color="#354157"
        text-color="#fff"
        active-text-color="#ffd04b"
        unique-opened
        :collapse="isCollapse"
        :collapse-transition="false"
        :router="true"
        default-active="0"
      >
        <!--一级菜单-->
        <el-menu-item index="/userwelcome">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>

        <el-submenu index="1">
          <!--一级菜单的模板-->
          <template slot="title">
            <!--图标-->
            <i class="el-icon-menu"></i>
            <!--文本-->
            <span>任务管理</span>
          </template>
          <!--二级菜单1-->
          <el-menu-item index="/goodtrans">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>订单配送任务</span>
            </template>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <!--一级菜单的模板-->
          <template slot="title">
            <!--图标-->
            <i class="el-icon-s-data"></i>
            <!--文本-->
            <span>查询管理</span>
          </template>
          <!--二级菜单-->
          <el-menu-item index="/usersearch">
            <template slot="title">
              <i class="el-icon-s-help"></i>
              <span>个人配送查询</span>
            </template>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="3">
          <!--一级菜单的模板-->
          <template slot="title">
            <!--图标-->
            <i class="el-icon-s-tools"></i>
            <!--文本-->
            <span>系统设置</span>
          </template>
          <!--二级菜单-->
          <el-menu-item index="/usupdatepwd">
            <template slot="title">
              <i class="el-icon-edit"></i>
              <span>修改密码</span>
            </template>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
    <el-container>
      <!--头部区域-->
      <el-header>
        <div>
          <span>冷链物流车辆调度平台</span>
        </div>
        <div>
          <el-button v-model="useracc" style="display:none"></el-button>
          <el-button v-model="userpwd" style="display:none"></el-button>
          <span id="useracc" style="font-size:15px;padding-right:20px">欢迎您，{{username}}！</span>
          <el-avatar
            :size="30"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            style="padding-right:0px"
          ></el-avatar>
          <el-dropdown @command="logout">
            <span class="el-dropdown-link" style="color:#fff;padding-left:1px">
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-switch-button" command="a">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <!-- <el-link type="warning" @click="logout" style="font-size:25px" :underline="false" icon="el-icon-switch-button"></el-link> -->
          <!-- <el-button type="warning" @click="logout" size="small" icon="el-icon-switch-button" round></el-button> -->
        </div>
      </el-header>
      <!--页面主体区-->
      <el-main>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      useracc: "",
      username:"",
      userpwd:"",
      openside: true,
      //菜单是否折叠
      isCollapse: false
    };
  },
  methods: {
    logout() {
      this.$session.destroy();
      this.$router.push("/login");
    },
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    opensideinfo() {
      let _this = this;
      _this.openside = false;
    },
    closesideinfo() {
      let _this = this;
      _this.openside = true;
    }
  },
  created() {
    // if (!this.$session.exists()) {
    //   this.$router.push("/login");
    // }
     this.useracc = this.$session.get("useraccount");
     this.username = this.$session.get("username");
     this.userpwd = this.$session.get("userpassword");
  }
};
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #354157;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #fff;
  font-size: 25px;
  > div {
    display: plex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}
.el-main {
  background-color: #eaedf1;
}
.el-aside {
  background-color: #354157;
}
.toggle-button {
  background-color: #243346;
  font-size: 30px;
  line-height: 60px;
  color: #fff;
  text-align: center;
  cursor: pointer;
}
</style>