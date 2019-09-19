package cn.lixinblog.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        String msg = "Hello, DirectProducer";
        System.out.println("DirectProducer ==>> " + msg);
        rabbitTemplate.convertAndSend("directQueue", msg);
    }

}
