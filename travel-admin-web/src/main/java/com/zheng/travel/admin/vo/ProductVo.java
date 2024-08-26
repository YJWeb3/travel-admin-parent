package com.zheng.travel.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo implements java.io.Serializable {
    // 分页
    private Integer pageNo = 1;
    private Integer pageSize = 15;
    // 搜索关键词
    private String keyword;
    // 搜索状态
    private Integer status;
    // 搜索分类id
    private Long categoryId;
    // 查询是否删除的
    private Integer isDelete = 0;
    // 批量删除的ids
    private String batchIds;
}
