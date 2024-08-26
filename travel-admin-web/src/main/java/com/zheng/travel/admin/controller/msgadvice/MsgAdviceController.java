package com.zheng.travel.admin.controller.msgadvice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.msgadvice.IMsgAdviceService;
import com.zheng.travel.admin.pojo.MsgAdvice;
import com.zheng.travel.admin.vo.MsgAdviceVo;
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
public class MsgAdviceController extends BaseController{

    private final IMsgAdviceService msgadviceService;


    /**
     * 查询公告管理列表信息
     */
    @GetMapping("/msgadvice/load")
    public List<MsgAdvice> findMsgAdviceList() {
        return msgadviceService.findMsgAdviceList();
    }

	/**
	 * 查询公告管理列表信息并分页
	*/
    @PostMapping("/msgadvice/list")
    public IPage<MsgAdvice> findMsgAdvices(@RequestBody MsgAdviceVo msgadviceVo) {
        return msgadviceService.findMsgAdvicePage(msgadviceVo);
    }


    /**
     * 根据公告管理id查询明细信息
    */
    @GetMapping("/msgadvice/get/{id}")
    public MsgAdvice getMsgAdviceById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return msgadviceService.getMsgAdviceById(new Long(id));
    }


	/**
	 * 保存和修改公告管理
	*/
    @PostMapping("/msgadvice/saveupdate")
    public MsgAdvice saveupdateMsgAdvice(@RequestBody MsgAdvice msgadvice) {
        return msgadviceService.saveupdateMsgAdvice(msgadvice);
    }


    /**
	 * 根据公告管理id删除公告管理
	*/
    @PostMapping("/msgadvice/delete/{id}")
    public int deleteMsgAdviceById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return msgadviceService.deleteMsgAdviceById(new Long(id));
    }


   /**
   	 * 根据公告管理ids批量删除公告管理
   	*/
    @PostMapping("/msgadvice/delBatch")
    public boolean delMsgAdvice(@RequestBody MsgAdviceVo msgadviceVo) {
        log.info("你要批量删除的IDS是:{}", msgadviceVo.getBatchIds());
        if (Vsserts.isEmpty(msgadviceVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return msgadviceService.delBatchMsgAdvice(msgadviceVo.getBatchIds());
    }

}