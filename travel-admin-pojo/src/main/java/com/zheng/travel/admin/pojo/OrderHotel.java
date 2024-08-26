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
@TableName("travel_user_order_hotel")
public class OrderHotel  implements java.io.Serializable {

    // 主健
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 订单编号 微信支付和阿里支付 用来退款
    private String orderno;
    // 下单用户id
    private Long userId;
    // 下单用户的昵称
    private String nickname;
    // 下单用户的头像
    private String avatar;
    // 订单状态 -1 失效的订单 0 未支付的订单 1 已支付 2已完成
    private Integer status;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 下单金额
    private String paymoney;
    // 酒店id
    private Long hotelId;
    // 酒店封面
    private String hotelImg;
    // 酒店标题
    private String hotelTitle;
    // 酒店描述
    private String hotelDesc;
    // 酒店房型标题
    private String hotelDetailTitle;
    // 酒店房型图标
    private String hotelDetailImg;
    // 酒店房型id
    private Long hotelDetailId;
    // 交易号，微信支付和阿里支付 方便退款
    private String tradeno;
    // 单金额
    private String openid;
    // 支付IP地址
    private String ip;
    // 支付用户的IP具体地址
    private String ipaddress;
    // 支付方式 1 微信支付 2 支付支付 3 待定
    private Integer paytype;
    // 酒店地址
    private String hotelAddress;
    // 酒店联系方式
    private String hotelTel;
    // 酒店经度
    private String hotelLgt;
    // 酒店纬度
    private String hotelLan;
    // 
    private Integer isdelete;

}