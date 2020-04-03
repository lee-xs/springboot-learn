package cn.aleestar.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String description;
    private String role;

}
