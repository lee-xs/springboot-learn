package cn.aleestar.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.two")
public class FanoutConsumer2 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("FanoutConsumer[2] <<== " + msg);
    }

}
