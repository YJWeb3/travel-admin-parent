package com.zheng.travel.admin.service.orderhotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.OrderHotel;
import com.zheng.travel.admin.vo.OrderHotelVo;
import java.util.List;
import java.util.Map;

public interface IOrderHotelService extends IService<OrderHotel>{


    /**
     * 统计订单相关的销售额
     * @return
     */
    List<Map<String, Object>> countStateOrderRelation();

    /**
     * 查询酒店订单列表信息
     */
    List<OrderHotel> findOrderHotelList() ;

	/**
     * 查询酒店订单列表信息并分页
    */
	IPage<OrderHotel> findOrderHotelPage(OrderHotelVo orderhotelVo);

    /**
     * 保存&修改酒店订单
    */
    OrderHotel saveupdateOrderHotel(OrderHotel orderhotel);

    /**
     * 根据Id删除酒店订单
     */
    int deleteOrderHotelById(Long id) ;

    /**
     * 根据Id查询酒店订单明细信息
    */
    OrderHotel getOrderHotelById(Long id);

    /**
     * 根据酒店订单ids批量删除酒店订单
    */
    boolean delBatchOrderHotel(String ids);

}