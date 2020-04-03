package cn.aleestar.service;

import cn.aleestar.entity.Role;
import cn.aleestar.entity.UserRole;
import cn.aleestar.mapper.RoleMapper;
import cn.aleestar.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户id查看角色信息
     * @param uid
     * @return
     */
    public Role findRoleByUid(Integer uid) {
        UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", uid));
        return roleMapper.selectOne(new QueryWrapper<Role>().eq("role_id", userRole.getRoleId()));
    }

}
