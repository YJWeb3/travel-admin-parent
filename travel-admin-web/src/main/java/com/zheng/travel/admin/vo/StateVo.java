package com.zheng.travel.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateVo implements java.io.Serializable  {
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
}