<template>
<div class="pwd">
  <a-form :form="form" @submit="handleSubmit">
    <a-form-item v-bind="formItemLayout" label="旧密码">
      <a-input v-decorator="[ 'old_password', { rules: [{ required: true, message: '请输入新密码', }, { validator: validatePsw, }], } ]" type="password" />
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="新密码">
      <a-input v-decorator="[ 'password', { rules: [{ required: true, message: '请输入新密码', }, { validator: validatePsw, }], } ]" type="password" />
    </a-form-item>
    <a-form-item v-bind="formItemLayout" label="确认密码">
      <a-input v-decorator="[ 'confirm', { rules: [{ required: true, message: '请再次输入密码', }, { validator: compareToFirstPassword, }], } ]" type="password" @blur="handleConfirmBlur" />
    </a-form-item>
    <a-form-item v-bind="formItemLayout">
      <a-button v-if="!confirmLoading" type="primary" html-type="submit"> 保存 </a-button>
      <a-button v-else type="primary" disabled html-type="submit"> <a-icon type="loading"/> 保存 </a-button>
    </a-form-item>

  </a-form>
</div>
</template>

<script>
import md5 from "md5";

export default {
  data() {
    return {
      confirmLoading: false,
      confirmDirty: false,
      autoCompleteResult: [],
      formItemLayout: {
        labelCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 4
          },
        },
        wrapperCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 10
          },
        },
      },
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        const {old_password, password, confirm} = values;
        if (!err) {
          this.confirmLoading = true;
          this._http.put(`/api/v3/change-pwd`, {
            old_password: md5(old_password),
            new_password: md5(password)
          }).then(res => {
            this.$message.success('密码修改成功');
            this.confirmLoading = false;
          }, err => {
            this.$message.warning(err.message);
            this.confirmLoading = false;
          })
        }
      });
    },
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    compareToFirstPassword(rule, value, callback) {
      const form = this.form;
      if (value && value !== form.getFieldValue('password')) {
        callback('密码输入不一致');
      } else {
        callback();
      }
    },
    validatePsw(rule, value, callback) {
      if (value) {
        if (value.length < 6) {
          callback('密码不能少于6位数');
        } else {
          callback();
        }
        if (value.length > 20 && value.length !== 32) {
          callback('密码不能大20位数');
        } else {
          callback();
        }
      } else {
        callback();
      }
    },
  },
};
</script>


<style lang="less">
.pwd {
    margin-top: 40px;
}
.ant-form-explain  {
  text-align: left;
}
</style>
