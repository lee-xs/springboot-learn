<template>
  <div>
    <h1>主页</h1>
    <h2 v-text="message"></h2>
  </div>
</template>

<script>
import { Index } from '@/api/index'
export default {
  data() {
    return {
      message: ""
    };
  },
  methods: {
    handleIndex() {
      Index().then(res => {
        this.message = res.message
      })
    }
  },
  created(){
    this.handleIndex()
  }
};
</script>
