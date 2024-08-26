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
@TableName("travel_hotel")
public class Hotel  implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 标题
    private String title;
    // 封面图
    private String img;
    // 图集列表
    private String imglists;
    // 描述
    private String description;
    // 价格
    private String price;
    // 相关信息
    private String relationinfo;
    // 真实价格
    private Float realprice;
    // 发布状态
    private Integer status;
    // 删除状态
    private Integer isdelete;
    // 浏览数数
    private Integer views;
    // 收藏数
    private Integer collects;
    // 购买数
    private Integer buynum;
    // 创建事件
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新事件
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 经度
    private String lan;
    // 纬度
    private String lgt;
    // 国家
    private String contury;
    // 城市
    private String city;
    // 区域
    private String area;
    // 省份
    private String province;
    // 省份编号
    private String provincecode;
    // 城市编号
    private String citycode;
    // 区域编号
    private String areacode;
    // 星级
    private Integer starlevel;
    // 酒店分类
    private Long hotelCategoryId;
    // 标签
    private String tags;
    // 酒店品牌分类
    private Long hotelBrandId;
    // 品牌名称
    private String hotelBrandName;
    // 酒店分类
    private String hotelCategoryName;
    // 通知
    private String advice;
    // 服务项目
    private String hotelServiceItem;
    // 评论数
    private Integer comments;
    // 地址
    private String address;
    // 联系方式
    private String phone;
    // 联系方式
    private Integer isopen;

    // 增加房型查询
    @TableField(exist = false)
    private List<HotelTypeMiddle> hotelTypeMiddleList;

}