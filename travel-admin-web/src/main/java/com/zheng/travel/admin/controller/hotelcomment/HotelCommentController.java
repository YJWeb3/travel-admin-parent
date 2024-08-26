package com.zheng.travel.admin.controller.hotelcomment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.hotelcomment.IHotelCommentService;
import com.zheng.travel.admin.pojo.HotelComment;
import com.zheng.travel.admin.vo.HotelCommentVo;
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
public class HotelCommentController extends BaseController{

    private final IHotelCommentService hotelcommentService;


    /**
     * 查询酒店评论列表信息
     */
    @GetMapping("/hotelcomment/load")
    public List<HotelComment> findHotelCommentList() {
        return hotelcommentService.findHotelCommentList();
    }

	/**
	 * 查询酒店评论列表信息并分页
	*/
    @PostMapping("/hotelcomment/list")
    public IPage<HotelComment> findHotelComments(@RequestBody HotelCommentVo hotelcommentVo) {
        return hotelcommentService.findHotelCommentPage(hotelcommentVo);
    }


    /**
     * 根据酒店评论id查询明细信息
    */
    @GetMapping("/hotelcomment/get/{id}")
    public HotelComment getHotelCommentById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelcommentService.getHotelCommentById(new Long(id));
    }


	/**
	 * 保存和修改酒店评论
	*/
    @PostMapping("/hotelcomment/saveupdate")
    public HotelComment saveupdateHotelComment(@RequestBody HotelComment hotelcomment) {
        return hotelcommentService.saveupdateHotelComment(hotelcomment);
    }


    /**
	 * 根据酒店评论id删除酒店评论
	*/
    @PostMapping("/hotelcomment/delete/{id}")
    public int deleteHotelCommentById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelcommentService.deleteHotelCommentById(new Long(id));
    }


   /**
   	 * 根据酒店评论ids批量删除酒店评论
   	*/
    @PostMapping("/hotelcomment/delBatch")
    public boolean delHotelComment(@RequestBody HotelCommentVo hotelcommentVo) {
        log.info("你要批量删除的IDS是:{}", hotelcommentVo.getBatchIds());
        if (Vsserts.isEmpty(hotelcommentVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return hotelcommentService.delBatchHotelComment(hotelcommentVo.getBatchIds());
    }

}