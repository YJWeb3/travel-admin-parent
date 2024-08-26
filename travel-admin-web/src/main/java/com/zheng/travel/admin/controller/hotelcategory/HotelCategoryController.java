package com.zheng.travel.admin.controller.hotelcategory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.service.hotelcategory.IHotelCategoryService;
import com.zheng.travel.admin.pojo.HotelCategory;
import com.zheng.travel.admin.vo.HotelCategoryVo;
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
public class HotelCategoryController extends BaseController{

    private final IHotelCategoryService hotelcategoryService;


    /**
     * 查询酒店分类列表信息
     */
    @GetMapping("/hotelcategory/load")
    public List<HotelCategory> findHotelCategoryList() {
        return hotelcategoryService.findHotelCategoryList();
    }

	/**
	 * 查询酒店分类列表信息并分页
	*/
    @PostMapping("/hotelcategory/list")
    public IPage<HotelCategory> findHotelCategorys(@RequestBody HotelCategoryVo hotelcategoryVo) {
        return hotelcategoryService.findHotelCategoryPage(hotelcategoryVo);
    }


    /**
     * 根据酒店分类id查询明细信息
    */
    @GetMapping("/hotelcategory/get/{id}")
    public HotelCategory getHotelCategoryById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelcategoryService.getHotelCategoryById(new Long(id));
    }


	/**
	 * 保存和修改酒店分类
	*/
    @PostMapping("/hotelcategory/saveupdate")
    public HotelCategory saveupdateHotelCategory(@RequestBody HotelCategory hotelcategory) {
        return hotelcategoryService.saveupdateHotelCategory(hotelcategory);
    }


    /**
	 * 根据酒店分类id删除酒店分类
	*/
    @PostMapping("/hotelcategory/delete/{id}")
    public int deleteHotelCategoryById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelcategoryService.deleteHotelCategoryById(new Long(id));
    }


   /**
   	 * 根据酒店分类ids批量删除酒店分类
   	*/
    @PostMapping("/hotelcategory/delBatch")
    public boolean delHotelCategory(@RequestBody HotelCategoryVo hotelcategoryVo) {
        log.info("你要批量删除的IDS是:{}", hotelcategoryVo.getBatchIds());
        if (Vsserts.isEmpty(hotelcategoryVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelcategoryService.delBatchHotelCategory(hotelcategoryVo.getBatchIds());
    }

}