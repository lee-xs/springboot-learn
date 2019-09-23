package cn.lixinblog.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String salt;
    private Integer valid;

    //并非数据库表的字段映射
    @Transient
    private Integer roleId;

}
