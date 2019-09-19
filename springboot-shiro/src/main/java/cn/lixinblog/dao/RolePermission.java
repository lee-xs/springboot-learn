package cn.lixinblog.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;

@Data
@ToString
@NoArgsConstructor
@Table(name = "role_permission")
public class RolePermission {

    private Integer permissionId;
    private Integer roleId;
}
