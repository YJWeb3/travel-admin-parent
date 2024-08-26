package com.zheng.travel.admin.service.hoteltype;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.HotelType;
import com.zheng.travel.admin.vo.HotelTypeVo;
import java.util.List;

public interface IHotelTypeService extends IService<HotelType>{


    /**
     * 查询酒店类型列表信息
     */
    List<HotelType> findHotelTypeList() ;

	/**
     * 查询酒店类型列表信息并分页
    */
	IPage<HotelType> findHotelTypePage(HotelTypeVo hoteltypeVo);

    /**
     * 保存&修改酒店类型
    */
    HotelType saveupdateHotelType(HotelType hoteltype);

    /**
     * 根据Id删除酒店类型
     */
    int deleteHotelTypeById(Long id) ;

    /**
     * 根据Id查询酒店类型明细信息
    */
    HotelType getHotelTypeById(Long id);

    /**
     * 根据酒店类型ids批量删除酒店类型
    */
    boolean delBatchHotelType(String ids);

}