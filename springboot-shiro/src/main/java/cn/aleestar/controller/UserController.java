package cn.aleestar.controller;

import cn.aleestar.dao.Role;
import cn.aleestar.dao.User;
import cn.aleestar.dto.Result;
import cn.aleestar.dto.StatusCode;
import cn.aleestar.service.RoleService;
import cn.aleestar.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("用户列表")
    @GetMapping("/list")
    public Result userList(){
        //查询用户列表
        List<User> userList = userService.findUserList();
        for(User user : userList){
            Role role = roleService.findRoleByUid(user.getId());
            user.setPassword("********")
                .setSalt("********")
                .setRoleId(role.getId());
        }
        return new Result(0, userList); // 0表示layui接收状态码
    }

    //@RequiresPermissions("更新用户")
    @RequiresRoles(value = {"超级管理员", "管理员"}, logical = Logical.OR)
    @PostMapping("/edit")
    public Result edit(@RequestBody User user){
        if(userService.edit(user) == 1){
            return new Result(StatusCode.OK, "修改成功");
        }
        return new Result(StatusCode.ERROR, "修改失败");
    }

    @RequiresPermissions("删除用户")
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
