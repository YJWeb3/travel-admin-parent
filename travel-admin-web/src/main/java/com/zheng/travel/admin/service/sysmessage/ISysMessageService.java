package com.zheng.travel.admin.service.sysmessage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.SysMessage;
import com.zheng.travel.admin.vo.SysMessageVo;
import java.util.List;

public interface ISysMessageService extends IService<SysMessage>{


    /**
     * 查询系统消息列表信息
     */
    List<SysMessage> findSysMessageList() ;

	/**
     * 查询系统消息列表信息并分页
    */
	IPage<SysMessage> findSysMessagePage(SysMessageVo sysmessageVo);

    /**
     * 保存&修改系统消息
    */
    SysMessage saveupdateSysMessage(SysMessage sysmessage);

    /**
     * 根据Id删除系统消息
     */
    int deleteSysMessageById(Long id) ;

    /**
     * 根据Id查询系统消息明细信息
    */
    SysMessage getSysMessageById(Long id);

    /**
     * 根据系统消息ids批量删除系统消息
    */
    boolean delBatchSysMessage(String ids);

}