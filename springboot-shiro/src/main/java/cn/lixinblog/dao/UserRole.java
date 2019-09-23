package cn.lixinblog.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@Table(name = "user_role")
public class UserRole {

    private Integer uid;
    private Integer roleId;

}
