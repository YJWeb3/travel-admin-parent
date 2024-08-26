package com.zheng.travel.admin.controller.sysmessage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.sysmessage.ISysMessageService;
import com.zheng.travel.admin.pojo.SysMessage;
import com.zheng.travel.admin.vo.SysMessageVo;
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
public class SysMessageController extends BaseController{

    private final ISysMessageService sysmessageService;


    /**
     * 查询系统消息列表信息
     */
    @GetMapping("/sysmessage/load")
    public List<SysMessage> findSysMessageList() {
        return sysmessageService.findSysMessageList();
    }

	/**
	 * 查询系统消息列表信息并分页
	*/
    @PostMapping("/sysmessage/list")
    public IPage<SysMessage> findSysMessages(@RequestBody SysMessageVo sysmessageVo) {
        return sysmessageService.findSysMessagePage(sysmessageVo);
    }


    /**
     * 根据系统消息id查询明细信息
    */
    @GetMapping("/sysmessage/get/{id}")
    public SysMessage getSysMessageById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return sysmessageService.getSysMessageById(new Long(id));
    }


	/**
	 * 保存和修改系统消息
	*/
    @PostMapping("/sysmessage/saveupdate")
    public SysMessage saveupdateSysMessage(@RequestBody SysMessage sysmessage) {
        return sysmessageService.saveupdateSysMessage(sysmessage);
    }


    /**
	 * 根据系统消息id删除系统消息
	*/
    @PostMapping("/sysmessage/delete/{id}")
    public int deleteSysMessageById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return sysmessageService.deleteSysMessageById(new Long(id));
    }


   /**
   	 * 根据系统消息ids批量删除系统消息
   	*/
    @PostMapping("/sysmessage/delBatch")
    public boolean delSysMessage(@RequestBody SysMessageVo sysmessageVo) {
        log.info("你要批量删除的IDS是:{}", sysmessageVo.getBatchIds());
        if (Vsserts.isEmpty(sysmessageVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return sysmessageService.delBatchSysMessage(sysmessageVo.getBatchIds());
    }

}