package cn.aleestar.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        String msg = "topic.message !!!";
        System.out.println("TopicProducer ==>> " + msg);
        rabbitTemplate.convertAndSend("topicExchange","topic.msg", msg);
    }

    public void sends(){
        String msg = "topic.messages !!!!!!! ##";
        System.out.println("TopicProducer ==>> " + msg);
        rabbitTemplate.convertAndSend("topicExchange","topic.AAAAAAAA", msg);
    }

}
