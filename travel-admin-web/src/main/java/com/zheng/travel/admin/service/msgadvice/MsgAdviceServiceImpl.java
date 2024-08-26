package com.zheng.travel.admin.service.msgadvice;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.MsgAdviceMapper;
import com.zheng.travel.admin.pojo.MsgAdvice;
import com.zheng.travel.admin.vo.MsgAdviceVo;
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
public class MsgAdviceServiceImpl extends ServiceImpl<MsgAdviceMapper,MsgAdvice> implements IMsgAdviceService  {

    /**
     * 查询分页&搜索公告管理
     */
    @Override
	public IPage<MsgAdvice> findMsgAdvicePage(MsgAdviceVo msgadviceVo){
	    // 设置分页信息
		Page<MsgAdvice> page = new Page<>(msgadviceVo.getPageNo(),msgadviceVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<MsgAdvice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(MsgAdvice.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(msgadviceVo.getKeyword()), MsgAdvice::getTitle,msgadviceVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(msgadviceVo.getStatus() != null ,MsgAdvice::getStatus,msgadviceVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(msgadviceVo.getKeyword()),wrapper -> wrapper
        //         .like(MsgAdvice::getName, msgadviceVo.getKeyword())
        //         .or()
        //         .like(MsgAdvice::getCategoryName, msgadviceVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(MsgAdvice::getCreateTime);
        // 查询分页返回
		IPage<MsgAdvice> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询公告管理列表信息
    */
    @Override
    public List<MsgAdvice> findMsgAdviceList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<MsgAdvice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(MsgAdvice::getStatus,1);
        lambdaQueryWrapper.eq(MsgAdvice::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询公告管理明细信息
     */
    @Override
    public MsgAdvice getMsgAdviceById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改公告管理
     */
    @Override
	public MsgAdvice saveupdateMsgAdvice(MsgAdvice msgadvice){
		boolean flag = this.saveOrUpdate(msgadvice);
		return flag ? msgadvice : null;
	}


    /**
     * 根据id删除公告管理
     */
    @Override
    public int deleteMsgAdviceById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchMsgAdvice(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<MsgAdvice>
            List<MsgAdvice> msgadviceList = Arrays.stream(strings).map(idstr -> {
                MsgAdvice msgadvice = new MsgAdvice();
                msgadvice.setId(new Long(idstr));
                msgadvice.setIsdelete(1);
                return msgadvice;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(msgadviceList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}