package cn.aleestar.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "helloQueue")
public class HelloConsumer1 {

    @RabbitHandler
    public void process(String message){
        System.out.println("Consumer[1] <<== " + message);
    }

}
