package cn.aleestar.service;

import cn.aleestar.dto.User;
import cn.aleestar.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public User findUserByName(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        List<User> userList = userMapper.selectByExample(example);
        if(userList.size() == 1){
            return userList.get(0);
        }
        return null;
    }
}
