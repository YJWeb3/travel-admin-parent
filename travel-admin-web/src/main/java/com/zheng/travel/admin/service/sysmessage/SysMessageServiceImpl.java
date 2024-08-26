package com.zheng.travel.admin.service.sysmessage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.SysMessageMapper;
import com.zheng.travel.admin.pojo.SysMessage;
import com.zheng.travel.admin.vo.SysMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper,SysMessage> implements ISysMessageService  {

    /**
     * 查询分页&搜索系统消息
     */
    @Override
	public IPage<SysMessage> findSysMessagePage(SysMessageVo sysmessageVo){
	    // 设置分页信息
		Page<SysMessage> page = new Page<>(sysmessageVo.getPageNo(),sysmessageVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<SysMessage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(SysMessage.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(sysmessageVo.getKeyword()), SysMessage::getTitle,sysmessageVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(sysmessageVo.getStatus() != null ,SysMessage::getStatus,sysmessageVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(sysmessageVo.getKeyword()),wrapper -> wrapper
        //         .like(SysMessage::getName, sysmessageVo.getKeyword())
        //         .or()
        //         .like(SysMessage::getCategoryName, sysmessageVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(SysMessage::getCreateTime);
        // 查询分页返回
		IPage<SysMessage> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询系统消息列表信息
    */
    @Override
    public List<SysMessage> findSysMessageList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<SysMessage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(SysMessage::getStatus,1);
        lambdaQueryWrapper.eq(SysMessage::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询系统消息明细信息
     */
    @Override
    public SysMessage getSysMessageById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改系统消息
     */
    @Override
	public SysMessage saveupdateSysMessage(SysMessage sysmessage){
		boolean flag = this.saveOrUpdate(sysmessage);
		return flag ? sysmessage : null;
	}


    /**
     * 根据id删除系统消息
     */
    @Override
    public int deleteSysMessageById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchSysMessage(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<SysMessage>
            List<SysMessage> sysmessageList = Arrays.stream(strings).map(idstr -> {
                SysMessage sysmessage = new SysMessage();
                sysmessage.setId(new Long(idstr));
                sysmessage.setIsdelete(1);
                return sysmessage;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(sysmessageList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}