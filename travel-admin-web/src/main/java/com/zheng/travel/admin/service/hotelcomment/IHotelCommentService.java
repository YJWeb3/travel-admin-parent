package com.zheng.travel.admin.service.hotelcomment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.HotelComment;
import com.zheng.travel.admin.vo.HotelCommentVo;
import java.util.List;

public interface IHotelCommentService extends IService<HotelComment>{


    /**
     * 查询酒店评论列表信息
     */
    List<HotelComment> findHotelCommentList() ;

	/**
     * 查询酒店评论列表信息并分页
    */
	IPage<HotelComment> findHotelCommentPage(HotelCommentVo hotelcommentVo);

    /**
     * 保存&修改酒店评论
    */
    HotelComment saveupdateHotelComment(HotelComment hotelcomment);

    /**
     * 根据Id删除酒店评论
     */
    int deleteHotelCommentById(Long id) ;

    /**
     * 根据Id查询酒店评论明细信息
    */
    HotelComment getHotelCommentById(Long id);

    /**
     * 根据酒店评论ids批量删除酒店评论
    */
    boolean delBatchHotelComment(String ids);

}