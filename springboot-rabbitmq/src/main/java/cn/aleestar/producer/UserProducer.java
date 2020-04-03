package cn.aleestar.producer;

import cn.aleestar.pojo.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * 实体类的传输（springboot完美的支持对象的发送和接收，不需要格外的配置。实体类必须序列化）
     * @param user
     */
    public void send(User user){
        System.out.println("UserProducer ==>> " + user.toString());
        amqpTemplate.convertAndSend("userQueue", user);
    }
}
