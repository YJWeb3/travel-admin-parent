package com.zheng.travel.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("travel_role_permission")
public class RolePermission implements java.io.Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;//	主键
    private Long permissionId;// 权限ID
    private Long roleId;//	角色id
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//	创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;//	更新时间

}
