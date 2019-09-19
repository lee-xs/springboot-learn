package cn.lixinblog.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        String msg = "Fanout Message";
        System.out.println("FanoutProducer ==>> " + msg);
        rabbitTemplate.convertAndSend("fanoutExchange","",msg);
    }

}
