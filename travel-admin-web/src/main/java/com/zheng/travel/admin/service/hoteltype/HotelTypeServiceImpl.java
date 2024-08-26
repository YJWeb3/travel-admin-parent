package com.zheng.travel.admin.service.hoteltype;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.HotelTypeMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.HotelType;
import com.zheng.travel.admin.vo.HotelTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HotelTypeServiceImpl extends ServiceImpl<HotelTypeMapper,HotelType> implements IHotelTypeService  {

    /**
     * 查询分页&搜索酒店类型
     */
    @Override
	public IPage<HotelType> findHotelTypePage(HotelTypeVo hoteltypeVo){
	    // 设置分页信息
		Page<HotelType> page = new Page<>(hoteltypeVo.getPageNo(),hoteltypeVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<HotelType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(HotelType.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(hoteltypeVo.getKeyword()), HotelType::getTitle,hoteltypeVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(hoteltypeVo.getStatus() != null ,HotelType::getStatus,hoteltypeVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(hoteltypeVo.getKeyword()),wrapper -> wrapper
        //         .like(HotelType::getName, hoteltypeVo.getKeyword())
        //         .or()
        //         .like(HotelType::getCategoryName, hoteltypeVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(HotelType::getCreateTime);
        // 查询分页返回
		IPage<HotelType> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询酒店类型列表信息
    */
    @Override
    public List<HotelType> findHotelTypeList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<HotelType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(HotelType::getStatus,1);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询酒店类型明细信息
     * @param id
     * @return HotelType
     * 创建人:yykk
     * 创建时间：2022-04-18 01:07:59
     * @version 1.0.0
     */
    @Override
    public HotelType getHotelTypeById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改酒店类型
     */
    @Override
	public HotelType saveupdateHotelType(HotelType hoteltype){
		boolean flag = this.saveOrUpdate(hoteltype);
		return flag ? hoteltype : null;
	}


    /**
     * 根据id删除酒店类型
     */
    @Override
    public int deleteHotelTypeById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchHotelType(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<HotelType>
            List<HotelType> hoteltypeList = Arrays.stream(strings).map(idstr -> {
                HotelType hoteltype = new HotelType();
                hoteltype.setId(new Long(idstr));
                return hoteltype;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(hoteltypeList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}