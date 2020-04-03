package cn.aleestar.config;

import cn.aleestar.config.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * http 请求认证配置
     * @param http 请求
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * addFilterBefore(): 自定义添加一个过滤器，并且放在Spring Security某个过滤器的前面
         * fromLogin(): 表单认证
         * httpBasic(): 弹出框认证
         * loginPage(): 登录页面地址（因为SpringBoot无法直接访问页面，所以这通常是一个路由地址）
         * loginProcessingUrl(): 登录表单提交地址
         * successHandler(): 自定义身份校验成功成功处理器
         * failureHandler(): 自定义身份校验失败失败处理器
         * authorizeRequests() 身份认证请求
         * anyRequest(): 所有请求
         * authenticated(): 身份认证
         * .csrf().disable(): 关闭Spring Security的跨站请求伪造的功能
         */

        http.authorizeRequests()
                .antMatchers("/index").hasAnyAuthority("角色列表")
                .anyRequest().authenticated()//任意请求需要登录
                .and()

                .formLogin()//开启formLogin默认配置
                .loginPage("/login").permitAll()//登陆页,授权全部权限，也就是不拦截得意思
                .loginProcessingUrl("/signin")//登入表单提交地址
                .usernameParameter("username")	//要认证的用户参数名，默认username
                .passwordParameter("password")	//要认证的密码参数名，默认password
                .and()

                .csrf().disable();//禁用csrf

        /**
         * 在上面写的配置类中添加一个配置loginProcessingUrl这个配置是定义登录表单提交之后是由那个资源去处理的。
         * 与其相关的配置有，用户名获取的字段，密码获取的字段以及登录失败后跳转的路径。
         */

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略静态文件不校验，假如有配置swagger，也是在这里配置
        web.ignoring().antMatchers("/static/**");
    }

    /**
     * 授权
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());//配置BCrypt加密
    }
}
