package cn.aleestar.realm;

import cn.aleestar.service.PermissionService;
import cn.aleestar.service.RoleService;
import cn.aleestar.service.UserService;
import cn.aleestar.dao.Permission;
import cn.aleestar.dao.Role;
import cn.aleestar.dao.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.warn("UserRealm[正在进行权限验证]");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo ();
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUsername(username);
        Role role = roleService.findRoleByUid(user.getId());

        //此处把当前subject对应的所有角色信息交给shiro，调用hasRole的时候就根据这些role信息判断
        authorizationInfo.addRole(role.getRole());
        List<Permission> permissionList = permissionService.findPermissions(user.getId());
        Set<String> permissionNameList = new HashSet<>();
        for (Permission permission : permissionList){
            permissionNameList.add(permission.getName());
        }
        //此处把当前subject对应的权限信息交给shiro，当调用hasPermission的时候就会根据这些信息判断
        authorizationInfo.setStringPermissions(permissionNameList);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /**
         * 这里为什么是String类型呢？其实要根据Subject.login(token)时候的token来的，你token定义成的pricipal是啥，这里get的时候就是啥。比如我
         * Subject subject = SecurityUtils.getSubject();
         * UsernamePasswordToken idEmail = new UsernamePasswordToken(String.valueOf(user.getId()), user.getEmail());
         * try {
         *   idEmail.setRememberMe(true);
         *   subject.login(idEmail);
         * }
         **/
        log.warn("UserRealm[正在进行身份验证]");
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.findUserByUsername(username);
        if(user == null) throw new UnknownAccountException();
        //SimpleAuthenticationInfo还有其他构造方法，比如密码加密算法等，感兴趣可以自己看
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                "UserRealm"
        );
        //设置盐值
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        //authenticationInfo信息交个shiro，调用login的时候会自动比较这里的token和authenticationInfo
        return authenticationInfo;
    }
}
