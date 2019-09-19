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
@Table(name = "permission")
public class Permission {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String description;

}
