package cn.lixinblog.service;

import cn.lixinblog.dao.Permission;
import cn.lixinblog.dao.Role;
import cn.lixinblog.dao.RolePermission;
import cn.lixinblog.mapper.PermissionMapper;
import cn.lixinblog.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class PermissionService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     *
     * 根据y用户Id查询所有的权限信息
     * @param id
     * @return
     */
    public List<Permission> findPermissions(Integer id){
        Role role = roleService.findRoleByUid(id);
        //List<Integer> roleIdList = new ArrayList<>();
        Example example = new Example(RolePermission.class);
        example.createCriteria().andEqualTo("roleId",role.getId());
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
}
