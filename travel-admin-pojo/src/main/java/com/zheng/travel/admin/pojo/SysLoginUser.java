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
@TableName("travel_users")
public class SysLoginUser  implements java.io.Serializable {

    // 
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 手机号
    private String phone;
    // 昵称，媒体号
    private String nickname;
    // 唯一标识
    private String idcode;
    // 头像
    private String avatar;
    // 性别 1:男  0:女  2:保密
    private Integer sex;
    // 生日
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date birthday;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;
    // 区县
    private String district;
    // 简介
    private String description;
    // 个人介绍的背景图
    private String bgImg;
    // 创建时间 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 激活状态 1 激活 0未激活
    private Integer status;
    // 密码
    private String password;
    // 微信登录openid
    private String openid;
    // 微信登录unionid
    private String unionid;
    // 
    private Integer isdelete;

}