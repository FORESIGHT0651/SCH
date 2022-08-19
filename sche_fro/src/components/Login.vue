<template>
  <div class="login_container">
    <!-- <img src="../assets/lorry.jpg" alt /> -->
    <div class="login_box">
      <!--登录表单-->
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginFormRules"
        label-width="0px"
        class="login_form"
      >
        <el-form-item prop="divider">
          <span style="color:#fff;font-size: 30px;">账户登录</span>
          <el-divider content-position="right"></el-divider>
        </el-form-item>
        <!--用户名-->
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="iconfont icon-user" clearable></el-input>
        </el-form-item>
        <!--密码-->
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="iconfont icon-3702mima"
            type="password"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item class="radio">
          <el-radio-group v-model="loginForm.radio">
            <el-radio label="1" style="color:#fff">调度员</el-radio>
            <el-radio label="2" style="color:#fff">司机</el-radio>
          </el-radio-group>
        </el-form-item>
        <!--按钮-->
        <el-form-item class="btns">
          <el-link
            style="padding-right:100px;color:#fff"
            @click="forgetpwd"
            :underline="false"
            icon="el-icon-key"
          >忘记密码?</el-link>
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
var code;
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        radio: "1"
      },
      //登录表单验证
      loginFormRules: {
        username: [
          { required: true, message: "请输入用户名!", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码!", trigger: "blur" }]
      }
    };
  },
  methods: {
    ...mapMutations(["changeLogin"]),

    resetLoginForm() {
      //console.log(this)
      this.$refs.loginFormRef.resetFields();
    },

    login() {
      if (this.loginForm.username === "" || this.loginForm.password === "") {
        alert("账号密码不能为空！");
      } else {
        if (this.loginForm.radio === "1") {
          const _this = this;
          axios.get("http://localhost:8282/admin/findAdminByAccPwd/" +_this.loginForm.username +"&" +_this.loginForm.password
            ).then(function(resp) {
              console.log(resp);
              if (resp.data.length != 0) {
                _this.$session.start();
                _this.$session.set("adminaccount", resp.data[0].adminaccount);
                _this.$session.set("adminname", resp.data[0].adminname);
                _this.$session.set("adminpassword", resp.data[0].adminpassword);
                _this.$session.set("adminpassword1", _this.$md5(resp.data[0].adminpassword));
                console.log(_this.$session.get("adminaccount"));
                _this.$router.push({
                  path: "/home",
                  query: {
                    adminaccount: resp.data[0].adminaccount,
                    //adminname: resp.data[0].adminname,
                    //adminpassword:resp.data[0].adminpassword,
                    adminpassword1:_this.$md5(resp.data[0].adminpassword)
                  }
                });
              } else{
                alert("账号密码错误！");
              }
            });
        } else if (this.loginForm.radio === "2") {
          const _this = this;
          axios.get("http://localhost:8282/user/findUserByAccPwd/" + _this.loginForm.username +"&"+_this.loginForm.password
            ).then(function(res) {
              console.log(res);
              if (res.data.length != 0) {
                _this.$session.start();
                _this.$session.set("useraccount", res.data[0].useraccount);
                _this.$session.set("username", res.data[0].username);
                _this.$session.set("userpassword",res.data[0].userpassword);
                 _this.$session.set("userpassword1", _this.$md5(res.data[0].userpassword));
                console.log(_this.$session.get("useraccount"));
                _this.$router.push({
                  path: "/homeuser",
                  query: {
                    useraccount: res.data[0].useraccount,
                    //username: res.data[0].username,
                    //userpassword:res.data[0].userpassword,
                    userpassword1:_this.$md5(res.data[0].userpassword),
                  }
                });
              } else {
                alert("账号密码错误！");
              }
            });
        }
      }
    },
  },
//   created(){
//      //验证码
//       code = "";
//       var codeLength = 4;
//      // var checkCode = document.getElementById("checkCode");
//       var selectChar = new Array(0,1,2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
//       for(var i=0;i<codeLength;i++){
//         var charIndex = Math.floor(Math.random()*36);
//         code += selectChar[charIndex];
//       }
//       console.log(code)
//     //  if(this.loginForm.checkCode){
//         // checkCode.className = "code";
//          //checkCode.value = code;
//         // checkCode.blur();
//        this.loginForm.code1 = code;
//       // this.loginForm.code1.blur()
//      // }
//   }
 };
</script>

<style lang="less" scoped>
.login_container {
  height: 100%;
  width: 100%;
  // background-color: #465b76;
  background: radial-gradient(circle, white, #384f6e);
  background: -ms-radial-gradient(circle, white, #384f6e);
  background: -webkit-radial-gradient(circle, white, #384f6e);
  background: -moz-radial-gradient(circle, white, #384f6e);
}
.login_box {
  width: 400px;
  height: 400px;
  background-color: rgba(#232323, 0.7);
  // background-color: #232323;
  border-radius: 4px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  //   -webkit-box-shadow: 0px 3px 3px #c8c8c8;
  //   -moz-box-shadow: 0px 3px 3px #c8c8c8;
  box-shadow: 1px 4px 4px #000, 8px 8px 8px #000, 8px 8px 8px #000,
    1px 4px 4px #000;
}
.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 25px;
  box-sizing: border-box;
}
.radio {
  display: flex;
  justify-content: flex-end;
}
.btns {
  display: flex;
  justify-content: flex-end;
}
.divider {
  position: absolute;
  justify-content: flex-start;
}
</style>