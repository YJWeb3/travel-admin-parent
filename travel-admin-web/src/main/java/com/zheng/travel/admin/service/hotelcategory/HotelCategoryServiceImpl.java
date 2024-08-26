package com.zheng.travel.admin.service.hotelcategory;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.HotelCategoryMapper;
import com.zheng.travel.admin.pojo.HotelCategory;
import com.zheng.travel.admin.vo.HotelCategoryVo;
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
public class HotelCategoryServiceImpl extends ServiceImpl<HotelCategoryMapper,HotelCategory> implements IHotelCategoryService  {

    /**
     * 查询分页&搜索酒店分类
     */
    @Override
	public IPage<HotelCategory> findHotelCategoryPage(HotelCategoryVo hotelcategoryVo){
	    // 设置分页信息
		Page<HotelCategory> page = new Page<>(hotelcategoryVo.getPageNo(),hotelcategoryVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<HotelCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(HotelCategory.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(hotelcategoryVo.getKeyword()), HotelCategory::getTitle,hotelcategoryVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(hotelcategoryVo.getStatus() != null ,HotelCategory::getStatus,hotelcategoryVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(hotelcategoryVo.getKeyword()),wrapper -> wrapper
        //         .like(HotelCategory::getName, hotelcategoryVo.getKeyword())
        //         .or()
        //         .like(HotelCategory::getCategoryName, hotelcategoryVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(HotelCategory::getCreateTime);
        // 查询分页返回
		IPage<HotelCategory> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询酒店分类列表信息
    */
    @Override
    public List<HotelCategory> findHotelCategoryList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<HotelCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(HotelCategory::getStatus,1);
        lambdaQueryWrapper.eq(HotelCategory::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询酒店分类明细信息
     */
    @Override
    public HotelCategory getHotelCategoryById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改酒店分类
     */
    @Override
	public HotelCategory saveupdateHotelCategory(HotelCategory hotelcategory){
		boolean flag = this.saveOrUpdate(hotelcategory);
		return flag ? hotelcategory : null;
	}


    /**
     * 根据id删除酒店分类
     */
    @Override
    public int deleteHotelCategoryById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchHotelCategory(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<HotelCategory>
            List<HotelCategory> hotelcategoryList = Arrays.stream(strings).map(idstr -> {
                HotelCategory hotelcategory = new HotelCategory();
                hotelcategory.setId(new Long(idstr));
                hotelcategory.setIsdelete(1);
                return hotelcategory;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(hotelcategoryList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}