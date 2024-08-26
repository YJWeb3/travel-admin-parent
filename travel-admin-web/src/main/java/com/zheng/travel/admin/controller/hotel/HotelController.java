package com.zheng.travel.admin.controller.hotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.commons.anno.TravelLog;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelBussinessException;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.Hotel;
import com.zheng.travel.admin.service.hotel.IHotelService;
import com.zheng.travel.admin.vo.HotelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HotelController extends BaseController {

    private final IHotelService hotelService;


    /**
     * 查询酒店管理列表信息
     */
    @GetMapping("/hotel/load")
    public List<Hotel> findHotelList() {
        return hotelService.findHotelList();
    }

    /**
     * 查询酒店管理列表信息并分页
     */
    @PostMapping("/hotel/list")
    public IPage<Hotel> findHotels(@RequestBody HotelVo hotelVo) {
        return hotelService.findHotelPage(hotelVo);
    }


    /**
     * 根据酒店管理id查询明细信息
     */
    @GetMapping("/hotel/get/{id}")
    public Hotel getHotelById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelService.getHotelById(new Long(id));
    }


    /**
     * 保存和修改酒店管理
     */
    @PostMapping("/hotel/saveupdate")
    @TravelLog(title = "保存酒店")
    public Hotel saveupdateHotel(@RequestBody Hotel hotel) {
        return hotelService.saveupdateHotel(hotel);
    }


    /**
     * 保存和修改酒店管理
     */
    @PostMapping("/hotel/copy/{id}")
    @TravelLog(title = "复制保存酒店")
    public Hotel copyHotel(@PathVariable("id") Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel == null) {
            throw new TravelBussinessException(ResultStatusEnumA.NULL_DATA);
        }
        return hotelService.copyHotel(hotel);
    }


    /**
     * 根据酒店管理id删除酒店管理
     */
    @PostMapping("/hotel/delete/{id}")
    @TravelLog(title = "酒店单删除")
    public int deleteHotelById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelService.deleteHotelById(new Long(id));
    }


    /**
     * 根据酒店管理ids批量删除酒店管理
     */
    @PostMapping("/hotel/delBatch")
    @TravelLog(title = "酒店删除")
    public boolean delHotel(@RequestBody HotelVo hotelVo) {
        log.info("你要批量删除的IDS是:{}", hotelVo.getBatchIds());
        if (Vsserts.isEmpty(hotelVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelService.delBatchHotel(hotelVo.getBatchIds());
    }

}