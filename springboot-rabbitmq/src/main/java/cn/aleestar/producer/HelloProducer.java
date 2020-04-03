package cn.aleestar.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class HelloProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 用于单生产者 -> 单消费者测试
     */
    public void send(String name){
        String sendMsg = "hello," + name + "," + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        System.out.println("Producer ==>> " + sendMsg);
        amqpTemplate.convertAndSend("helloQueue", sendMsg);
    }

    /**
     * 用于单/多生产者 -> 多消费者测试
     */
    public void sends(String name,int num){
        String sendMsg = "hello," + name + "::[" + num + "]::" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        System.out.println("Producer ==>> " + sendMsg);
        amqpTemplate.convertAndSend("helloQueue", sendMsg);
    }
}
