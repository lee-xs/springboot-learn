package cn.aleestar.interceptor;

import cn.aleestar.annotation.LoginToken;
import cn.aleestar.annotation.PassToken;
import cn.aleestar.service.UserService;
import cn.aleestar.utils.TokenUtils;
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
        response.setContentType("application/json;charset=utf-8");
        //如果不是映射到方法直接通过
        if(!(obj instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) obj;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                log.warn("token -> 跳过认证");
                return true;
            }
        }

        //检查是否有loginToken注解，有则需要认证
        if(method.isAnnotationPresent(LoginToken.class)){
            log.warn("token -> 需要认证");
            String token = request.getHeader("Authorization");
            //token不存在
            if(token == null){
                log.warn("token -> 无token，请重新登录");
                response.getWriter().write("无token，请重新登录");
                return false;
            }

            //验证token
            if(TokenUtils.verifyToken(token) == null){
                log.warn("token -> token验证不通过");
                response.getWriter().write("token验证不通过");
                return false;
            }
            log.warn("token -> 验证通过");
            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
