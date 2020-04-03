package cn.aleestar.config.service;

import cn.aleestar.entity.Permission;
import cn.aleestar.entity.User;
import cn.aleestar.mapper.UserMapper;
import cn.aleestar.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /**
     * Spring Security接收login请求调用UserDetailsService这个接口中的loadUserByUsername方法
     * loadUserByUsername根据传进来的用户名进行校验工作，最后将查询到的用户信息封装到UserDetails这个接口的实现类中
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException 登录异常类
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));

        if(user == null){
            //抛出用户不存在异常
            throw new UsernameNotFoundException("用户不存在");
        }

        List<Permission> permissionList = permissionService.findPermissions(user.getId());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        //组装权限GrantedAuthority object
        permissionList.forEach(permission -> {
            if(permission != null && permission.getPermissionName() != null){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionName());
                grantedAuthorityList.add(grantedAuthority);
            }
        });
        //返回用户信息
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorityList);
    }

}
