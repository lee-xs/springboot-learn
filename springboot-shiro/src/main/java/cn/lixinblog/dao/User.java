package cn.lixinblog.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
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

}
