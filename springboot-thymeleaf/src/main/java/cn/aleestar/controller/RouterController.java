package cn.aleestar.controller;

import cn.aleestar.dao.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RouterController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("user",new User("admin","admin"));

        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        model.addAttribute("list", list);
        return "index";
    }

}
