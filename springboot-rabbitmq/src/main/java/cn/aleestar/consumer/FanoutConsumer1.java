package cn.aleestar.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.one")
public class FanoutConsumer1 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("FanoutConsumer[1] <<== " + msg);
    }

}
