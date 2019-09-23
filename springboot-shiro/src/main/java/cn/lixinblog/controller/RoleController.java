package cn.lixinblog.controller;

import cn.lixinblog.dto.Result;
import cn.lixinblog.dto.StatusCode;
import cn.lixinblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public Result findRoleAll(){
        return new Result(StatusCode.OK, roleService.findRoleAll());
    }

}
