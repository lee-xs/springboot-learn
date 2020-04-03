package cn.aleestar.service;

import cn.aleestar.dao.Role;
import cn.aleestar.dao.User;
import cn.aleestar.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@SuppressWarnings("all")
public class UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 根据ID查询用户数据
     * @param id
     * @return
     */
    public User findUserById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据用户名查询用户数据
     * @param username
     * @return
     */
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
        Role role = roleService.findRoleById(user.getRoleId());
        int success2 = userRoleService.add(user.getId(), role.getId());
        return success1 + success2;
    }

    public List<User> findUserList() {
        return userMapper.selectAll();
    }

    @Transactional
    public Integer edit(User user) {
        Example example = new Example(User.class);
        User oldUser = findUserById(user.getId());
        oldUser.setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setValid(user.getValid());

        Role role = roleService.findRoleByUid(oldUser.getId());
        //判断是否修改了角色
        if(user.getRoleId() != role.getId()){
            userRoleService.edit(oldUser.getId(), user.getRoleId());
        }

        return userMapper.updateByPrimaryKey(oldUser);
    }

    @Transactional
    public Integer delete(Integer id) {
        userRoleService.delete(id);
        return userMapper.deleteByPrimaryKey(id);
    }
}
