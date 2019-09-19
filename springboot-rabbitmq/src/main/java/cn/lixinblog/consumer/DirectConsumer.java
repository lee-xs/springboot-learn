package cn.lixinblog.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "directQueue")
public class DirectConsumer {

    @RabbitHandler
    public void process(String msg){
        System.out.println("DirectConsumer <<== " + msg);
    }

}
