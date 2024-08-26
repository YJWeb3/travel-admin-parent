package com.zheng.travel.admin.service.hoteltypemiddle;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.HotelTypeMiddle;
import com.zheng.travel.admin.vo.HotelTypeMiddleVo;

import java.util.List;

public interface IHotelTypeMiddleService extends IService<HotelTypeMiddle> {


    /**
     * 查询酒店房型信息
     * @param hotelTypeMiddleVo
     * @return
     */
    List<HotelTypeMiddle> findHotelTypeInfos(HotelTypeMiddleVo hotelTypeMiddleVo);
}
