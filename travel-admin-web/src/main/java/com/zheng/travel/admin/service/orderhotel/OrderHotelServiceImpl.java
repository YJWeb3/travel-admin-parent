package com.zheng.travel.admin.service.orderhotel;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.OrderHotelMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.OrderHotel;
import com.zheng.travel.admin.vo.OrderHotelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderHotelServiceImpl extends ServiceImpl<OrderHotelMapper,OrderHotel> implements IOrderHotelService  {


    /**
     * 统计订单相关的销售额
     * @return
     */
    @Override
    public List<Map<String, Object>> countStateOrderRelation(){
        return this.baseMapper.countStateOrderRelation();
    }

    /**
     * 查询分页&搜索酒店订单
     */
    @Override
	public IPage<OrderHotel> findOrderHotelPage(OrderHotelVo orderhotelVo){
	    // 设置分页信息
		Page<OrderHotel> page = new Page<>(orderhotelVo.getPageNo(),orderhotelVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<OrderHotel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(OrderHotel.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(orderhotelVo.getKeyword()), OrderHotel::getNickname,orderhotelVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(orderhotelVo.getStatus() != null ,OrderHotel::getStatus,orderhotelVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(orderhotelVo.getKeyword()),wrapper -> wrapper
        //         .like(OrderHotel::getName, orderhotelVo.getKeyword())
        //         .or()
        //         .like(OrderHotel::getCategoryName, orderhotelVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(OrderHotel::getCreateTime);
        // 查询分页返回
		IPage<OrderHotel> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询酒店订单列表信息
    */
    @Override
    public List<OrderHotel> findOrderHotelList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<OrderHotel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(OrderHotel::getStatus,1);
        lambdaQueryWrapper.eq(OrderHotel::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询酒店订单明细信息
     */
    @Override
    public OrderHotel getOrderHotelById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改酒店订单
     */
    @Override
	public OrderHotel saveupdateOrderHotel(OrderHotel orderhotel){
		boolean flag = this.saveOrUpdate(orderhotel);
		return flag ? orderhotel : null;
	}


    /**
     * 根据id删除酒店订单
     */
    @Override
    public int deleteOrderHotelById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchOrderHotel(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<OrderHotel>
            List<OrderHotel> orderhotelList = Arrays.stream(strings).map(idstr -> {
                OrderHotel orderhotel = new OrderHotel();
                orderhotel.setId(new Long(idstr));
                orderhotel.setIsdelete(1);
                return orderhotel;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(orderhotelList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}