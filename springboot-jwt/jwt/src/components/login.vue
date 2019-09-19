<template>
  <div>
    <h1>登录页</h1>
    <input type="text" v-model="username" />
    <input type="password" v-model="password" />
    <button @click="handleLogin()">登录</button>
    <h2>
      显示登录状态：
      <span v-text="message">当前未登录</span>
    </h2>
  </div>
</template>

<script>
import { login } from '@/api/login'
import { setToken } from "@/utils/auth";
export default {
  data() {
    return {
      username: "",
      password: "",
      message: ""
    };
  },
  methods: {
    handleLogin() {
      login(this.username, this.password).then(res => {
        setToken(res.token)
        this.message = res.message
      }).catch(err => {
        this.message = err.message
      })
    }
  }
};
</script>
