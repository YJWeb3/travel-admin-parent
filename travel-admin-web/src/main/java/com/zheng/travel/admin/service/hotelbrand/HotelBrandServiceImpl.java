package com.zheng.travel.admin.service.hotelbrand;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.HotelBrandMapper;
import com.zheng.travel.admin.pojo.HotelBrand;
import com.zheng.travel.admin.vo.HotelBrandVo;
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
public class HotelBrandServiceImpl extends ServiceImpl<HotelBrandMapper,HotelBrand> implements IHotelBrandService  {

    /**
     * 查询分页&搜索酒店品牌
     */
    @Override
	public IPage<HotelBrand> findHotelBrandPage(HotelBrandVo hotelbrandVo){
	    // 设置分页信息
		Page<HotelBrand> page = new Page<>(hotelbrandVo.getPageNo(),hotelbrandVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<HotelBrand> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(HotelBrand.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(hotelbrandVo.getKeyword()), HotelBrand::getTitle,hotelbrandVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(hotelbrandVo.getStatus() != null ,HotelBrand::getStatus,hotelbrandVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(hotelbrandVo.getKeyword()),wrapper -> wrapper
        //         .like(HotelBrand::getName, hotelbrandVo.getKeyword())
        //         .or()
        //         .like(HotelBrand::getCategoryName, hotelbrandVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(HotelBrand::getCreateTime);
        // 查询分页返回
		IPage<HotelBrand> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询酒店品牌列表信息
    */
    @Override
    public List<HotelBrand> findHotelBrandList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<HotelBrand> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(HotelBrand::getStatus,1);
        lambdaQueryWrapper.eq(HotelBrand::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询酒店品牌明细信息
     */
    @Override
    public HotelBrand getHotelBrandById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改酒店品牌
     */
    @Override
	public HotelBrand saveupdateHotelBrand(HotelBrand hotelbrand){
		boolean flag = this.saveOrUpdate(hotelbrand);
		return flag ? hotelbrand : null;
	}


    /**
     * 根据id删除酒店品牌
     */
    @Override
    public int deleteHotelBrandById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchHotelBrand(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<HotelBrand>
            List<HotelBrand> hotelbrandList = Arrays.stream(strings).map(idstr -> {
                HotelBrand hotelbrand = new HotelBrand();
                hotelbrand.setId(new Long(idstr));
                hotelbrand.setIsdelete(1);
                return hotelbrand;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(hotelbrandList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}