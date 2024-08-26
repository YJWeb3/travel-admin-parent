package com.zheng.travel.admin.service.hotelbrand;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.HotelBrand;
import com.zheng.travel.admin.vo.HotelBrandVo;
import java.util.List;

public interface IHotelBrandService extends IService<HotelBrand>{


    /**
     * 查询酒店品牌列表信息
     */
    List<HotelBrand> findHotelBrandList() ;

	/**
     * 查询酒店品牌列表信息并分页
    */
	IPage<HotelBrand> findHotelBrandPage(HotelBrandVo hotelbrandVo);

    /**
     * 保存&修改酒店品牌
    */
    HotelBrand saveupdateHotelBrand(HotelBrand hotelbrand);

    /**
     * 根据Id删除酒店品牌
     */
    int deleteHotelBrandById(Long id) ;

    /**
     * 根据Id查询酒店品牌明细信息
    */
    HotelBrand getHotelBrandById(Long id);

    /**
     * 根据酒店品牌ids批量删除酒店品牌
    */
    boolean delBatchHotelBrand(String ids);

}