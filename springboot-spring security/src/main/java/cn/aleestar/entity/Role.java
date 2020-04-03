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
@TableName(value = "s_role")
public class Role {

    //主键 id
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    //角色名
    private String roleName;

    //描述
    private String readme;

    //创建时间
    private Date createTime;

    //上次修改时间
    private Date modifyTime;


}
