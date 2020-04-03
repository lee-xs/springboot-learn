package cn.aleestar.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.three")
public class FanoutConsumer3 {

    @RabbitHandler
    public void process(String msg){
        System.out.println("FanoutConsumer[3] <<== " + msg);
    }

}
