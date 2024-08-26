package com.zheng.travel.admin.controller.hotelbrand;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.service.hotelbrand.IHotelBrandService;
import com.zheng.travel.admin.pojo.HotelBrand;
import com.zheng.travel.admin.vo.HotelBrandVo;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.zheng.travel.admin.controller.BaseController;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class HotelBrandController extends BaseController{

    private final IHotelBrandService hotelbrandService;

    /**
     * 查询酒店品牌列表信息
     */
    @GetMapping("/hotelbrand/load")
    public List<HotelBrand> findHotelBrandList() {
        return hotelbrandService.findHotelBrandList();
    }

	/**
	 * 查询酒店品牌列表信息并分页
	*/
    @PostMapping("/hotelbrand/list")
    public IPage<HotelBrand> findHotelBrands(@RequestBody HotelBrandVo hotelbrandVo) {
        return hotelbrandService.findHotelBrandPage(hotelbrandVo);
    }


    /**
     * 根据酒店品牌id查询明细信息
    */
    @GetMapping("/hotelbrand/get/{id}")
    public HotelBrand getHotelBrandById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelbrandService.getHotelBrandById(new Long(id));
    }


	/**
	 * 保存和修改酒店品牌
	*/
    @PostMapping("/hotelbrand/saveupdate")
    public HotelBrand saveupdateHotelBrand(@RequestBody HotelBrand hotelbrand) {
        return hotelbrandService.saveupdateHotelBrand(hotelbrand);
    }


    /**
	 * 根据酒店品牌id删除酒店品牌
	*/
    @PostMapping("/hotelbrand/delete/{id}")
    public int deleteHotelBrandById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelbrandService.deleteHotelBrandById(new Long(id));
    }


   /**
   	 * 根据酒店品牌ids批量删除酒店品牌
   	*/
    @PostMapping("/hotelbrand/delBatch")
    public boolean delHotelBrand(@RequestBody HotelBrandVo hotelbrandVo) {
        log.info("你要批量删除的IDS是:{}", hotelbrandVo.getBatchIds());
        if (Vsserts.isEmpty(hotelbrandVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelbrandService.delBatchHotelBrand(hotelbrandVo.getBatchIds());
    }

}