package com.zheng.travel.admin.controller.orderhotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.orderhotel.IOrderHotelService;
import com.zheng.travel.admin.pojo.OrderHotel;
import com.zheng.travel.admin.vo.OrderHotelVo;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.zheng.travel.admin.controller.BaseController;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderHotelController extends BaseController{

    private final IOrderHotelService orderhotelService;


    /**
     * 统计订单相关的销售x
     * @return
     */
    @PostMapping("/orderhotel/state/relation")
    public List<Map<String, Object>> countStateOrderRelation() {
        return orderhotelService.countStateOrderRelation();
    }


    /**
     * 查询酒店订单列表信息
     */
    @GetMapping("/orderhotel/load")
    public List<OrderHotel> findOrderHotelList() {
        return orderhotelService.findOrderHotelList();
    }

	/**
	 * 查询酒店订单列表信息并分页
	*/
    @PostMapping("/orderhotel/list")
    public IPage<OrderHotel> findOrderHotels(@RequestBody OrderHotelVo orderhotelVo) {
        return orderhotelService.findOrderHotelPage(orderhotelVo);
    }


    /**
     * 根据酒店订单id查询明细信息
    */
    @GetMapping("/orderhotel/get/{id}")
    public OrderHotel getOrderHotelById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return orderhotelService.getOrderHotelById(new Long(id));
    }


	/**
	 * 保存和修改酒店订单
	*/
    @PostMapping("/orderhotel/saveupdate")
    public OrderHotel saveupdateOrderHotel(@RequestBody OrderHotel orderhotel) {
        return orderhotelService.saveupdateOrderHotel(orderhotel);
    }


    /**
	 * 根据酒店订单id删除酒店订单
	*/
    @PostMapping("/orderhotel/delete/{id}")
    public int deleteOrderHotelById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return orderhotelService.deleteOrderHotelById(new Long(id));
    }


   /**
   	 * 根据酒店订单ids批量删除酒店订单
   	*/
    @PostMapping("/orderhotel/delBatch")
    public boolean delOrderHotel(@RequestBody OrderHotelVo orderhotelVo) {
        log.info("你要批量删除的IDS是:{}", orderhotelVo.getBatchIds());
        if (Vsserts.isEmpty(orderhotelVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return orderhotelService.delBatchOrderHotel(orderhotelVo.getBatchIds());
    }

}