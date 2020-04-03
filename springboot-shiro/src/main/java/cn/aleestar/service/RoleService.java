package cn.aleestar.service;

import cn.aleestar.dao.Role;
import cn.aleestar.dao.UserRole;
import cn.aleestar.mapper.RoleMapper;
import cn.aleestar.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@SuppressWarnings("all")
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    /**
     * 根据用户id查询角色信息
     * @param id
     * @return
     */
    public Role findRoleByUid(Integer uid){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("uid",uid);
        //查询user id下的所有角色id
        List<UserRole> userRoleList = userRoleMapper.selectByExample(example);
        Role role = null;
        if(userRoleList.size() == 1){
            role = roleMapper.selectByPrimaryKey(userRoleList.get(0).getRoleId());
        }
        return role;
    }


    public List<Role> findRoleAll(){
        return roleMapper.selectAll();
    }

    public Role findRoleById(Integer roleId){
        return roleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 根据角色名获取角色信息
     * @param role
     * @return
     */
    public Role findRoleByRoleName(String role){
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo("role", role);
        List<Role> list = roleMapper.selectByExample(example);
        if(list.size() == 1){
            return list.get(0);
        }
        return null;
    }

}
