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
@TableName("travel_category")
public class IndexCategory  implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 标题
    private String title;
    // 描述
    private String description;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 发布状态
    private Integer status;
    // 排序
    private Integer sorted;
    // 删除状态
    private Integer isdelete;

}