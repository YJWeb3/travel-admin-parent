package com.zheng.travel.admin.service.msgadvice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.MsgAdvice;
import com.zheng.travel.admin.vo.MsgAdviceVo;
import java.util.List;

public interface IMsgAdviceService extends IService<MsgAdvice>{


    /**
     * 查询公告管理列表信息
     */
    List<MsgAdvice> findMsgAdviceList() ;

	/**
     * 查询公告管理列表信息并分页
    */
	IPage<MsgAdvice> findMsgAdvicePage(MsgAdviceVo msgadviceVo);

    /**
     * 保存&修改公告管理
    */
    MsgAdvice saveupdateMsgAdvice(MsgAdvice msgadvice);

    /**
     * 根据Id删除公告管理
     */
    int deleteMsgAdviceById(Long id) ;

    /**
     * 根据Id查询公告管理明细信息
    */
    MsgAdvice getMsgAdviceById(Long id);

    /**
     * 根据公告管理ids批量删除公告管理
    */
    boolean delBatchMsgAdvice(String ids);

}