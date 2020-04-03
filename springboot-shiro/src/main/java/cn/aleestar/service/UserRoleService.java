package cn.aleestar.service;

import cn.aleestar.dao.UserRole;
import cn.aleestar.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@SuppressWarnings("all")
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 修改用户对应的角色
     * @param uid
     * @param roleId
     * @return
     */
    public Integer edit(Integer uid, Integer roleId){
        UserRole userRole = new UserRole();
        userRole.setUid(uid).setRoleId(roleId);
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("uid", uid);
        return userRoleMapper.updateByExample(userRole,example);
    }

    /**
     * 绑定用户和角色的关系
     * @param uid
     * @param roleId
     * @return
     */
    public int add(Integer uid, Integer roleId){
        UserRole userRole = new UserRole();
        userRole.setUid(uid).setRoleId(roleId);
        return userRoleMapper.insert(userRole);
    }

    /**
     * 删除用户对应的角色
     * @param uid
     */
    public void delete(Integer uid){
        Example example = new Example(UserRole.class);
        example.createCriteria().andEqualTo("uid", uid);
        //删除用户对应的角色
        userRoleMapper.deleteByExample(example);
    }

}
