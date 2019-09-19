package cn.lixinblog.consumer;

import cn.lixinblog.pojo.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "userQueue")
public class UserConsumer {

    @RabbitHandler
    public void process(User user){
        System.out.println("UserConsumer <<== " + user.toString());
    }
}
