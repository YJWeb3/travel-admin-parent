package com.zheng.orm.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zheng.travel.admin.pojo.OrderHotel;

import java.util.List;
import java.util.Map;

public interface OrderHotelMapper extends BaseMapper<OrderHotel>{

    /**
     * 统计订单相关的销售额
     * @return
     */
    List<Map<String, Object>> countStateOrderRelation();
}