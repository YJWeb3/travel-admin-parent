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
@TableName("travel_hotel_category")
public class HotelCategory  implements java.io.Serializable {

    // 主健
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 分类名称
    private String title;
    // 描述
    private String description;
    // 状态 0未发布 1发布
    private Integer status;
    // 排序
    private Integer sorted;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 删除状态0未删除1删除
    private Integer isdelete;

}