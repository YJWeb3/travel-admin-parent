package com.zheng.travel.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("travel_pca")
public class Pca implements java.io.Serializable {

    @TableId(type = IdType.INPUT)
    private Integer id;
    private String name;
    private Integer pid;

    // 临时字段
    @TableField(exist = false)
    private List<Pca> childrens;
}
