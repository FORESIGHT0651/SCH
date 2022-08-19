<template>
<div>
  <!--导航-->
  <el-breadcrumb separator="/">
    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>
      <a href="/">系统设置</a>
    </el-breadcrumb-item>
    <el-breadcrumb-item>修改密码</el-breadcrumb-item>
  </el-breadcrumb>
  <el-card style="height:620px">
  <el-form
    :model="ruleForm"
    status-icon
    :rules="rules"
    ref="ruleForm"
    label-width="100px"
    class="demo-ruleForm"
  >
    <el-form-item label="旧密码" prop="oldpass" style="width:30%">
      <el-input type="password" v-model="ruleForm.oldpass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newpass" style="width:30%">
      <el-input type="password" v-model="ruleForm.newpass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkpass" style="width:30%">
      <el-input type="password" v-model="ruleForm.checkpass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
  </el-card>
  </div>
</template>
<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkpass !== "") {
          this.$refs.ruleForm.validateField("checkpass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.newpass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        oldpass:"",
        newpass: "",
        checkpass: "",
      },
      rules: {
        newpass: [{ validator: validatePass, trigger: "blur" }],
        checkpass: [{ validator: validatePass2, trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm() {
      console.log(this.$session.get("adminpassword"))
      if(this.ruleForm.oldpass === this.$session.get("adminpassword")){
        const _this = this;
        axios.put("http://localhost:8282/admin/updateAdminPwd/" 
          + _this.ruleForm.newpass +"&"+this.$session.get("adminaccount")
         ).then(function(res) {
            console.log(res);
            if(res.data == "success"){
              alert("密码修改成功！")
              _this.$session.destroy();
              _this.$router.push("/login");
            }else{
              alert("密码修改失败！")
            }
        })
      }else{
        alert("旧密码输入错误，请重新输入！")
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>