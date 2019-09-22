package cn.lixinblog.controller;

import cn.lixinblog.dto.Result;
import cn.lixinblog.dto.StatusCode;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class LoginController {


    @PostMapping("/handleLogin")
    public Result handleLogin(String username, String password, HttpServletRequest request){
        System.out.println(username + " :: " + password);
        HttpSession session = request.getSession();
        JSONObject jsonObject = new JSONObject();

        UsernamePasswordToken upt = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(upt); //登录
            log.warn("是否登录成功 :: " + subject.isAuthenticated());
            session.setAttribute("token", subject.getPrincipal());
            jsonObject.put("token", subject.getPrincipal());
            return new Result(StatusCode.OK, jsonObject);
        }catch (UnknownAccountException uae){
            jsonObject.put("message", "未知用户");
        }catch (IncorrectCredentialsException ice){
            jsonObject.put("message","密码不正确");
        }catch (LockedAccountException lae){
            jsonObject.put("message","账户已锁定");
        }catch (ExcessiveAttemptsException eae){
            jsonObject.put("message","用户名或密码错误次数过多");
        }catch (AuthenticationException ae){
            ae.printStackTrace();
            jsonObject.put("message","用户名或密码不正确");
        }
        return new Result(StatusCode.ERROR, jsonObject);
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("token");
        return new Result(StatusCode.OK, "");
    }

}
