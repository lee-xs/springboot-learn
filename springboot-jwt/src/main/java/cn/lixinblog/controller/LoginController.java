package cn.lixinblog.controller;

import cn.lixinblog.annotation.LoginToken;
import cn.lixinblog.annotation.PassToken;
import cn.lixinblog.dto.User;
import cn.lixinblog.service.UserService;
import cn.lixinblog.utils.TokenUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PassToken
    @PostMapping("/login")
    public Object login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        User user = userService.findUserByName(username);
        if(user == null){
            jsonObject.put("message", "用户不存在");
            return jsonObject;
        }

        if(!user.getPassword().equals(password)){
            jsonObject.put("message", "密码错误");
            return jsonObject;
        }

        String token = TokenUtils.createToken(user);

        jsonObject.put("message","登录成功");
        jsonObject.put("token", token);
        return jsonObject;
    }

    @LoginToken
    @GetMapping("/index")
    public Object index(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "验证通过");
        return jsonObject;
    }



}
