package com.zheng.travel.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("travel_role")
public class Role  implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 角色名字
    private String name;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 角色代号
    private String code;
    // 发布状态 1发布中 0未发布
    private Integer status;
    // 删除状态 0未删除 1删除
    private Integer isdelete;
    // 建立一个临时字段
    @TableField(exist = false)
    private Boolean isGrant = false;

}