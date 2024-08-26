package com.zheng.travel.admin.service.hotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.Hotel;
import com.zheng.travel.admin.vo.HotelVo;
import java.util.List;

public interface IHotelService extends IService<Hotel>{


    /**
     * 查询酒店管理列表信息
     */
    List<Hotel> findHotelList() ;

	/**
     * 查询酒店管理列表信息并分页
    */
	IPage<Hotel> findHotelPage(HotelVo hotelVo);

    /**
     * 保存&修改酒店管理
    */
    Hotel saveupdateHotel(Hotel hotel);


    /**
     * 克隆酒店
     */
    Hotel copyHotel(Hotel hotel);

    /**
     * 根据Id删除酒店管理
     */
    int deleteHotelById(Long id) ;

    /**
     * 根据Id查询酒店管理明细信息
    */
    Hotel getHotelById(Long id);

    /**
     * 根据酒店管理ids批量删除酒店管理
    */
    boolean delBatchHotel(String ids);

}