package cn.aleestar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "s_user_role")
public class UserRole {

    //用户id
    private Integer userId;

    //角色id
    private Integer roleId;
}
