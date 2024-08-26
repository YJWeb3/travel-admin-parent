package com.zheng.travel.admin.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("travel_nav_menu")
public class NavMenu implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 菜单名词
    private String name;
    // 菜单排序
    private Integer sorted;
    // 菜单链接
    private String path;
    // 菜单图标
    private String icon;
    // 菜单发布
    private Integer status;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 菜单名称
    private Long pid;
    // 组件名称
    private String componentname;
    // 路径名称
    private String pathname;
    // 父组件
    private String layout;
    // 排序
    private Integer indexon;
    // 是否展示
    private Integer showflag;
    // 子集
    @TableField(exist = false)
    private List<NavMenu> childrens;
}