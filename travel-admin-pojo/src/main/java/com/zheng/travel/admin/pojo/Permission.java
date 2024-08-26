package com.zheng.travel.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.zheng.travel.admin.commons.anno.TravelFeild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("travel_permission")
public class Permission  implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    @TravelFeild("ID")
    private Long id;
    // 权限名称
    @TravelFeild("权限名称")
    private String name;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    @TravelFeild("创建时间")
    private Date createTime;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @TravelFeild("更新时间")
    private Date updateTime;
    // 权限代号
    @TravelFeild("权限代号")
    private String code;
    // 权限URL
    @TravelFeild("权限URL")
    private String url;
    // 权限排序
    @TravelFeild("排序")
    private Integer sorted;
    // 删除状态 0未删除 1删除
    @TravelFeild("删除状态")
    private Integer isdelete;
    // 发布状态 1发布中 0 未发布
    @TravelFeild("发布状态")
    private Integer status;
    // 父id
    @TravelFeild("父ID")
    private Long pid;
    // 路径名
    @TravelFeild("路径名")
    private String pathname;

}