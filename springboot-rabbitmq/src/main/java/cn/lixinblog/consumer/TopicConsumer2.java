package cn.lixinblog.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topicMessages")
public class TopicConsumer2 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("TopicConsumer[2] <<== " + msg);
    }

}
