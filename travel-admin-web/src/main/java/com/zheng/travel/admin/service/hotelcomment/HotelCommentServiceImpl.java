package com.zheng.travel.admin.service.hotelcomment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.HotelCommentMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.HotelComment;
import com.zheng.travel.admin.vo.HotelCommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelCommentServiceImpl extends ServiceImpl<HotelCommentMapper,HotelComment> implements IHotelCommentService  {

    /**
     * 查询分页&搜索酒店评论
     */
    @Override
	public IPage<HotelComment> findHotelCommentPage(HotelCommentVo hotelcommentVo){
	    // 设置分页信息
		Page<HotelComment> page = new Page<>(hotelcommentVo.getPageNo(),hotelcommentVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<HotelComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(HotelComment.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(hotelcommentVo.getKeyword()), HotelComment::getUsernickname,hotelcommentVo.getKeyword());
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(hotelcommentVo.getKeyword()), HotelComment::getReplyUsernickname,hotelcommentVo.getKeyword());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(hotelcommentVo.getKeyword()),wrapper -> wrapper
        //         .like(HotelComment::getName, hotelcommentVo.getKeyword())
        //         .or()
        //         .like(HotelComment::getCategoryName, hotelcommentVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(HotelComment::getCreateTime);
        // 查询分页返回
		IPage<HotelComment> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询酒店评论列表信息
    */
    @Override
    public List<HotelComment> findHotelCommentList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<HotelComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询酒店评论明细信息
     */
    @Override
    public HotelComment getHotelCommentById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改酒店评论
     */
    @Override
	public HotelComment saveupdateHotelComment(HotelComment hotelcomment){
		boolean flag = this.saveOrUpdate(hotelcomment);
		return flag ? hotelcomment : null;
	}


    /**
     * 根据id删除酒店评论
     */
    @Override
    public int deleteHotelCommentById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchHotelComment(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<HotelComment>
            List<HotelComment> hotelcommentList = Arrays.stream(strings).map(idstr -> {
                HotelComment hotelcomment = new HotelComment();
                hotelcomment.setId(new Long(idstr));
                return hotelcomment;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(hotelcommentList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}