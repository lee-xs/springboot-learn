package cn.lixinblog.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topicMessage")
public class TopicConsumer1 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("TopicConsumer[1] <<== " + msg);
    }

}
