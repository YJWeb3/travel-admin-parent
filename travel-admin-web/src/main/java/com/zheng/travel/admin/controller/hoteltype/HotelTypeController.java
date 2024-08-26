package com.zheng.travel.admin.controller.hoteltype;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.hoteltype.IHotelTypeService;
import com.zheng.travel.admin.pojo.HotelType;
import com.zheng.travel.admin.vo.HotelTypeVo;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.zheng.travel.admin.controller.BaseController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HotelTypeController extends BaseController{

    private final IHotelTypeService hoteltypeService;


    /**
     * 查询酒店类型列表信息
     */
    @GetMapping("/hoteltype/load")
    public List<HotelType> findHotelTypeList() {
        return hoteltypeService.findHotelTypeList();
    }

	/**
	 * 查询酒店类型列表信息并分页
	*/
    @PostMapping("/hoteltype/list")
    public IPage<HotelType> findHotelTypes(@RequestBody HotelTypeVo hoteltypeVo) {
        return hoteltypeService.findHotelTypePage(hoteltypeVo);
    }


    /**
     * 根据酒店类型id查询明细信息
    */
    @GetMapping("/hoteltype/get/{id}")
    public HotelType getHotelTypeById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hoteltypeService.getHotelTypeById(new Long(id));
    }


	/**
	 * 保存和修改酒店类型
	*/
    @PostMapping("/hoteltype/saveupdate")
    public HotelType saveupdateHotelType(@RequestBody HotelType hoteltype) {
        return hoteltypeService.saveupdateHotelType(hoteltype);
    }


    /**
	 * 根据酒店类型id删除酒店类型
	*/
    @PostMapping("/hoteltype/delete/{id}")
    public int deleteHotelTypeById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hoteltypeService.deleteHotelTypeById(new Long(id));
    }


   /**
   	 * 根据酒店类型ids批量删除酒店类型
   	*/
    @PostMapping("/hoteltype/delBatch")
    public boolean delHotelType(@RequestBody HotelTypeVo hoteltypeVo) {
        log.info("你要批量删除的IDS是:{}", hoteltypeVo.getBatchIds());
        if (Vsserts.isEmpty(hoteltypeVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hoteltypeService.delBatchHotelType(hoteltypeVo.getBatchIds());
    }

}