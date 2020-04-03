package cn.aleestar.service;

import cn.aleestar.mapper.PermissionMapper;
import cn.aleestar.entity.Permission;
import cn.aleestar.entity.Role;
import cn.aleestar.entity.RolePermission;
import cn.aleestar.mapper.RolePermissionMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     *
     * 根据用户Id查询所有的权限信息
     * @param uid
     * @return
     */
    public List<Permission> findPermissions(Integer uid){
        List<Permission> permissionList = new ArrayList<>();
        Role role = roleService.findRoleByUid(uid);

        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", role.getRoleId()));
        rolePermissionList.forEach(rolePermission -> {
            Permission permission = permissionMapper.selectOne(new QueryWrapper<Permission>().eq("permission_id", rolePermission.getPermissionId()));
            permissionList.add(permission);
        });

        return permissionList;
    }
}
