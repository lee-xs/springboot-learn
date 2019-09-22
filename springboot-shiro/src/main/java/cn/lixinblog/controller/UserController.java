package cn.lixinblog.controller;

import cn.lixinblog.dao.User;
import cn.lixinblog.dto.Result;
import cn.lixinblog.dto.StatusCode;
import cn.lixinblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result userList(){
        //查询用户列表
        List<User> userList = userService.findUserList();
        for(int i = 0; i < userList.size(); i++){
            userList.get(i).setPassword("********");
            userList.get(i).setSalt("********");
        }
        return new Result(0, userList); // 0表示layui接收状态码
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody User user){
        System.out.println(user.toString());
        if(userService.edit(user) == 1){
            return new Result(StatusCode.OK, "修改成功");
        }
        return new Result(StatusCode.ERROR, "修改失败");
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        if(userService.delete(id) == 1){
            return new Result(StatusCode.OK, "删除成功");
        }
        return new Result(StatusCode.ERROR, "删除失败");
    }


    @GetMapping("/add")
    public Result userAdd(HttpServletRequest request,ModelMap modelMap){

        return null;
    }

}
