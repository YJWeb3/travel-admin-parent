package com.zheng.travel.admin.service.hotelcategory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.HotelCategory;
import com.zheng.travel.admin.vo.HotelCategoryVo;
import java.util.List;

public interface IHotelCategoryService extends IService<HotelCategory>{


    /**
     * 查询酒店分类列表信息
     */
    List<HotelCategory> findHotelCategoryList() ;

	/**
     * 查询酒店分类列表信息并分页
    */
	IPage<HotelCategory> findHotelCategoryPage(HotelCategoryVo hotelcategoryVo);

    /**
     * 保存&修改酒店分类
    */
    HotelCategory saveupdateHotelCategory(HotelCategory hotelcategory);

    /**
     * 根据Id删除酒店分类
     */
    int deleteHotelCategoryById(Long id) ;

    /**
     * 根据Id查询酒店分类明细信息
    */
    HotelCategory getHotelCategoryById(Long id);

    /**
     * 根据酒店分类ids批量删除酒店分类
    */
    boolean delBatchHotelCategory(String ids);

}