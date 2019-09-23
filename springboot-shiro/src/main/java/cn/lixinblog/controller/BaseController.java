package cn.lixinblog.controller;

import cn.lixinblog.dto.Result;
import cn.lixinblog.dto.StatusCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaseController {

    @ExceptionHandler({UnauthorizedException.class})
    public Result authorizationException(){
        return new Result(StatusCode.ERROR, "无操作权限！");
    }

}
