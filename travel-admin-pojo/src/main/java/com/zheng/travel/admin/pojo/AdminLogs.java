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
@TableName("travel_admin_logs")
public class AdminLogs  implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 类名
    private String classname;
    // 方法
    private String classmethod;
    // 描述
    private String description;
    // 执行用户
    private Long userId;
    // 执行用户名
    private String username;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 删除状态 0未删除 1删除
    private Integer isdelete;
    // 发布状态 1 发布 0未发布
    private Integer status;
    // ip地址
    private String ip;
    // ip地址
    private String ipaddress;
    // 运行的操作系统
    private String osversion;
    // 运行的浏览器
    private String broversion;
    // 方法执行的时间
    private Long methodtime;
    // 方法的参数获取
    private String methodparams;
    // 省份
    private String province;
    // 城市
    private String city;

}