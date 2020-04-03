package cn.aleestar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "s_user")
public class User {

    //主键 id
    @TableId(type = IdType.AUTO)
    private Integer id;

    //用户名
    private String username;

    //密码
    private String password;

    //邮箱
    private String email;

    //盐值
    private String salt;

    /**
     * 状态 0锁定 1有效
     */
    private Integer status;

    //创建时间
    private Date createTime;

    //上次修改时间
    private Date modifyTime;

    //最后登录时间
    private Date lastLoginTime;

    //地址
    private String address;

    //IP地址
    private String ip;

    /**
     * 性别 0女 1男 2保密
     */
    private Integer sex;

    //头像
    private String avatar;

}
