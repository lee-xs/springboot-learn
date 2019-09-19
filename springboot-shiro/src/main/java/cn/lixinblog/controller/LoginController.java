package cn.lixinblog.controller;

import cn.lixinblog.dao.User;
import cn.lixinblog.dto.Result;
import cn.lixinblog.dto.StatusCode;
import cn.lixinblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request,ModelMap modelMap){
        //查询用户列表
        List<User> userList = userService.findUserList();
        for(int i = 0; i < userList.size(); i++){
            userList.get(i).setPassword("********");
            userList.get(i).setSalt("********");
        }
        modelMap.addAttribute("userList", userList);

        HttpSession session = request.getSession();
        modelMap.addAttribute("token", session.getAttribute("token"));
        return "index";
    }

    @RequestMapping(value = {"/", "/login"})
    public String login(){
        return "login";
    }

    @ResponseBody
    @PostMapping("/handleLogin")
    public Result handleLogin(String username, String password, HttpServletRequest request, ModelMap modelMap){
        System.out.println(username + password);
        HttpSession session = request.getSession();

        UsernamePasswordToken upt = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(upt); //登录
            log.warn("是否登录成功 :: " + subject.isAuthenticated());
            session.setAttribute("token", subject.getPrincipal());
            modelMap.addAttribute("token", subject.getPrincipal());
            return new Result(StatusCode.OK, modelMap);
        }catch (UnknownAccountException uae){
            log.warn("用户名：[" + username + "] ==>> 验证未通过,未知用户");
            modelMap.addAttribute("message", "未知用户");
        }catch (IncorrectCredentialsException ice){
            log.warn("用户名：[" + username + "] ==>> 验证未通过,错误凭证");
            modelMap.addAttribute("message","密码不正确");
        }catch (LockedAccountException lae){
            log.warn("用户名：[" + username + "] ==>> 验证未通过,账户已锁定");
            modelMap.addAttribute("message","账户已锁定");
        }catch (ExcessiveAttemptsException eae){
            log.warn("用户名：[" + username + "] ==>> 验证未通过,错误次数过多");
            modelMap.addAttribute("message","用户名或密码错误次数过多");
        }catch (AuthenticationException ae){
            log.warn("用户名：[" + username + "] ==>> 验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            modelMap.addAttribute("message","用户名或密码不正确");
        }
        return new Result(StatusCode.ERROR, modelMap);
    }

    public String logout(){

        return null;
    }

    @GetMapping("/userList")
    public String userList(HttpServletRequest request,ModelMap modelMap){

        return "/userList";
    }
}
