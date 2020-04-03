package cn.aleestar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class RouterController {

    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap modelMap){
        HttpSession session = request.getSession();
        modelMap.addAttribute("token", session.getAttribute("token"));
        return "index";
    }

    @RequestMapping(value = {"/", "/login"})
    public String login(){
        return "login";
    }

}
