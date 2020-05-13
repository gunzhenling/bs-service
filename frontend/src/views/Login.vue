<template>
<div class="">
  <div class="container">
    <div class="title ant-btn-link" type=""> {{title}} </div>
    <div class="sub"> 登录您的账号 </div>
    <a-form :form="form" @submit="handleSubmit">
      <a-form-item @pressEnter="handleSubmit">
        <a-input size="large" placeholder="请输入姓名或者邮箱号"
          v-decorator="[ 'account', {rules: [{ required: true, message: '请输入姓名或者邮箱号' }]} ]">
          <a-icon slot="prefix" type="user" />
          <!-- <a-icon v-if="userName" slot="suffix" type="close-circle" @click="emitEmpty" /> -->
        </a-input>
      </a-form-item>
      <a-form-item @pressEnter="handleSubmit">
        <a-input size="large" id="pwd" placeholder="请输入密码" maxlength="20" type="password"
          v-decorator="[ 'pwd', {rules: [{ min: 6, message: '密码长度必须为6-20位' },{ max: 20, message: '密码长度必须为6-20位' }]} ]">
          <a-icon slot="prefix" type="lock" />
        </a-input>
      </a-form-item>
      <a-checkbox :checked="remember" @change="remember = !remember" style="margin-bottom:20px">记住账号密码</a-checkbox>
      <a-button class="login" size="large" type="primary" html-type="submit" @click="handleSubmit">
        登录
      </a-button>
    </a-form>
  </div>
</div>
</template>
<script>
import md5 from "md5";
export default {
  data() {
    return {
      title: document.title,
      form: this.$form.createForm(this),
      remember: false,
      statusAccount: "",
      statusPwd: "",
      helpAccount: "",
      helpPwd: "",
      labelCol: {
        xs: {
          span: 24
        },
        sm: {
          span: 5
        },
      },
      wrapperCol: {
        xs: {
          span: 24
        },
        sm: {
          span: 12
        },
      },
    };
  },
  mounted () {
    let account = JSON.parse(localStorage.account || "{}");
    this.form.setFieldsValue({
      account: account.account,
      pwd: account.pwd,
    });
    this.remember = account.remember;
  },
  methods: {
    handleSubmit: async function (e) {
      e.preventDefault();
      await this.form.validateFields((errors, values) => {
        if (errors) {
          if (errors.account) {
            this.helpAccount = errors.account.errors[0].message
          }
          if (errors.pwd) {
            this.helpPwd = errors.pwd.errors[0].message
          }
          return ;
        }
        this._http.post("/api/v3/login", {"account": values.account,"password":md5(values.pwd)}).then(res => {
          if (res.code) {
            this.$message.error(res.message);
          } else {
            if (this.remember) {
              localStorage.account = JSON.stringify({account: values.account, pwd: values.pwd, remember: true});
            } else {
              localStorage.account = "";
            }
            let user = res.result;
            this._http.setUser(user);
            localStorage.user = JSON.stringify(user);
            if (user.role == 0) {
              this.$router.push("/manager");
            } else {
              this.$router.push("/admin/user");
            }
          }
        })
      });
    }
  }
};
</script>

<style scoped lang="less">
.container {
    position: fixed;
    top: 200px;
    left: 0;
    right: 0;
    margin: auto;
    width: 400px;
    height: auto;
    padding: 40px;
    text-align: left;
}
.title {
    font-size: 24px;
    font-weight: bold;
    margin-top: 20px;
}
.sub {
    color: #999;
    font-weight: bold;
    margin-bottom: 30px;
    font-size: 18px;
}
.login {
    width: 100%;
}
</style>
