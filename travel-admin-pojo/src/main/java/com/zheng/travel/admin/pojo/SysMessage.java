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
@TableName("travel_sys_message")
public class SysMessage  implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    // 标题
    private String title;
    // 消息内容
    private String content;
    // 消息类型
    private Integer type;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 发布状态 1发布中 0未发布
    private Integer status;
    // 删除状态 0未删除 1删除
    private Integer isdelete;

}