package cn.lixinblog.controller;

import cn.lixinblog.pojo.User;
import cn.lixinblog.producer.DirectProducer;
import cn.lixinblog.producer.HelloProducer;
import cn.lixinblog.producer.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部分测试用例写在测试类中
 */
@RestController
public class TotalController {

    @Autowired
    private HelloProducer helloProducer;

    @Autowired
    private UserProducer userProducer;

    @Autowired
    private DirectProducer directProducer;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name){
        helloProducer.send(name);
        return "/hello 信息发送完毕！";
    }

    @GetMapping("/onetomany")
    public String oneToMany(@RequestParam("name") String name){
        for(int i = 0; i < 20; i++){
            helloProducer.sends(name, i);
        }
        return "/onetomany 信息发送完毕！";
    }


    @GetMapping("/c")
    public String user(){
        User user = new User();
        user.setName("admin");
        user.setPass("admin");
        userProducer.send(user);
        return "/user 信息发送完毕！";
    }

    @GetMapping("/direct")
    public String direct(){
        directProducer.send();
        return "/direct 信息发送完毕！";
    }

}
