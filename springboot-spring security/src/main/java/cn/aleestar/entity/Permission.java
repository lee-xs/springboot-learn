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
@TableName(value = "s_permission")
public class Permission {

    //主键 id
    @TableId(type = IdType.AUTO)
    private Integer PermissionId;

    //权限名
    private String permissionName;

    //描述
    private String readme;

    //创建时间
    private Date createTime;

    //上次修改时间
    private Date modifyTime;


}
