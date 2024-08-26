package com.zheng.travel.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexCategoryVo implements java.io.Serializable  {

    // 查询的id
	private Long id;
	// 查询状态
	private Integer status;
	// 当前页
	private Integer pageNo = 1;
	// 每页显示多少条
	private Integer pageSize = 10;
	// 搜索关键词
	private String keyword;
	// 批量删除的ids
    private String batchIds;
    // 查询是否删除的
    private Integer isDelete = 0;
    // 搜索分类id
    private Long categoryId;

}