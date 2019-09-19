package cn.lixinblog.service;

import cn.lixinblog.dao.*;
import cn.lixinblog.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 根据用户id查询所有的角色信息
     * @param id
     * @return
     */
    public List<Role> findRoles(Integer id){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("uid",id);
        //查询user id下的所有角色id
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
        List<Integer> roleIdList = new ArrayList<>();
        for(UserRole userRole : userRoleList){
            roleIdList.add(userRole.getRoleId());
        }
        Example roleExample = new Example(Role.class);
        roleExample.createCriteria().andIn("role_id", roleIdList);
        //查询角色id对应的角色
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        return roleList;
    }

    /**
     *
     * 根据y用户Id查询所有的权限信息
     * @param id
     * @return
     */
    public List<Permission> findPermissions(Integer id){
        List<Role> roleList = findRoles(id);
        List<Integer> roleIdList = new ArrayList<>();
        for(Role role : roleList){
            roleIdList.add(role.getId());
        }
        Example example = new Example(RolePermission.class);
        example.createCriteria().andIn("role_id",roleIdList);
        //查询所有角色 和 权限的关联关系
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(example);
        List<Integer> permissionIdList = new ArrayList<>();
        for(RolePermission rolePermission : rolePermissionList){
            permissionIdList.add(rolePermission.getPermissionId());
        }

        Example permissionExample = new Example(Permission.class);
        permissionExample.createCriteria().andIn("id",permissionIdList);
        //查询所有的权限
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        return permissionList;
    }

    public User findUserById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public User findUserByUsername(String username){
        User user = null;
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);
        List<User> list = userMapper.selectByExample(example);
        if(list.size() == 1) user = list.get(0);
        return user;
    }


    @Transactional
    public int add(User user){
        int success1 = userMapper.insert(user);
        UserRole userRole = new UserRole();
        userRole.setUid(user.getId());
        userRole.setRoleId(3);
        int success2 = userRoleMapper.insert(userRole);
        return success1 + success2;
    }

    public List<User> findUserList() {
        return userMapper.selectAll();
    }
}
