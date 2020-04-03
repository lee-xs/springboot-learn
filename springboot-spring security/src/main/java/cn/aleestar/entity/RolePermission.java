package cn.aleestar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "s_role_permission")
public class RolePermission {

    //角色id
    private Integer roleId;

    //权限id
    private Integer permissionId;

}
