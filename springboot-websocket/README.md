# Spring Boot 整合 Websocket

## 知识摘要

`Websocket` 是一种网络通信协议。

和HTTP协议不同，HTTP协议通信只能由客户端发起。比如我们常见的CRUD都仅仅是服务端请求客户端的接口，最终消息由客户端推送给服务端，服务端再实现数据回显等。

`Websocket` 最大的特点就是实现了服务器可以主动向客户端推送消息，客户端也可以主动向服务器发送消息，实现了真正的双向平等会话。

如果在服务端实现`Websocket`主动向客户端推送消息，需要使用JavaScript的`WebSocket`对象，它是JavaScript内置的对象，比如我们创建一个`Websocket`连接对象

## 环境准备

1.依赖引入

```xml
<!-- Spring Websocket -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

2.配置WebSocket需要自动注入的Servlet
```java
@Configuration
public class WebSocketService {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
```

## 代码实现

1.后台Java代码

```java
@Component
@ServerEndpoint("/websocket")
public class WebSocketService {

    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    /**
     * 静态变量，用来记录当前在线连接数，应该将其设计为线程安全的
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的websocket对象
     */
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接对话，需要通过session来给客户端发送数据
     */
    private Session session;


    /**
     * 连接建立成功调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        WebSocketService.timer();
        log.warn("新连接接入。当前在线人数："+getOnlineCount());
    }

    /**
     * 收到客户端消息后调用
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("客户端发送的消息：" + message);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        log.warn("有连接关闭。当前在线人数：" + getOnlineCount());
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.error("WebSocketService发生异常");
        error.printStackTrace();
    }

    /**
     * 暴露给外部的群发
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        sendAll(message);
    }

    /**
     * 群发发送消息
     * @param message
     */
    private static void sendAll(String message){
        Arrays.asList(webSocketSet.toArray()).forEach(item ->  {
            WebSocketService webSocketService = (WebSocketService)item;
            try{
                webSocketService.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 当前在线人数
     * @return
     */
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    /**
     * 减少在线人数
     */
    private void subOnlineCount(){
        WebSocketService.onlineCount--;
    }

    /**
     * 增加在线人数
     */
    private void addOnlineCount(){
        WebSocketService.onlineCount++;
    }

    /**
     * 定时器 ，定时向客户端发送当前时间
     */
    private static Timer timer = new Timer();
    public static void timer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                sendAll(time);
            }
        }, 5000,2000);
    }

}
```

2.前台Vue代码
```vue
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
```