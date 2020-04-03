package cn.aleestar.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

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
