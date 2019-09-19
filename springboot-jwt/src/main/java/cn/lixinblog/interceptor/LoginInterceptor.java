package cn.lixinblog.interceptor;

import cn.lixinblog.annotation.PassToken;
import cn.lixinblog.service.UserService;
import cn.lixinblog.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        log.warn("LoginInterceptor拦截器......");
        //如果不是映射到方法直接通过
        if(!(obj instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) obj;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            log.warn("token -> 跳过认证");
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }else{
            log.warn("token -> 开始认证");
            String token = request.getHeader("Authorization");
            log.warn("验证token ->" + token);
            //token不存在
            if(token == null){
                log.warn("token -> 无token，请重新登录");
                throw new RuntimeException("无token，请重新登录");
            }

            //验证token
            System.out.println(TokenUtils.verifyToken(token) == null);
            if(TokenUtils.verifyToken(token) == null){
                log.warn("token -> 不通过");
                throw new RuntimeException("验证不通过");
            }
            log.warn("token -> 验证通过");
            return true;
        }

        log.warn("ddddddddddddddd");

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
