<template>
  <div>
    <h1 v-text="msg"></h1>
  </div>
</template>

<script>
  export default {
    data(){
      return{
        ws: '',
        msgData: [],
        msg: 'NULL',
      }
    },
    created(){
      this.initWebSocket();
    },
    destroyed(){
      this.webSocketOnClose();
    },
    methods:{
      initWebSocket(){
        this.ws = new WebSocket("ws://localhost:2922/websocket");
        this.ws.onopen = this.webSocketOnOpen;
        this.ws.onmessage = this.webSocketOnMessage;
        this.ws.onclose = this.webSocketOnClose;
        this.ws.onerror = this.webSocketOnError;
      },
      webSocketOnOpen(){
        console.log("webSocket连接成功")
        this.msg = "webSocket连接成功"
        this.ws.send("客户端发送的消息！！！")
      },
      webSocketOnMessage(e){
        this.msg = e.data
        this.msgData.unshift(e)
        console.log(this.msgData)
      },
      webSocketOnClose: function(e){
        console.log("connection closed (" + e + ")");
        this.msg = "connection closed (" + e + ")"
      },
      webSocketOnError(e){
        console.log("webSocket连接发生错误")
        this.msg = "webSocket连接发生错误"
      }
    },
  }
</script>
