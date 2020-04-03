package cn.aleestar.controller;

import cn.aleestar.dto.Result;
import cn.aleestar.dto.StatusCode;
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
