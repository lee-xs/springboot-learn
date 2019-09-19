package cn.lixinblog.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "helloQueue")
public class HelloConsumer2 {

    @RabbitHandler
    public void process(String message){
        System.out.println("Consumer[2] <<== " + message);
    }

}
