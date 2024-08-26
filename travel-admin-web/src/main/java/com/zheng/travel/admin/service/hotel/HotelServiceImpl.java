package com.zheng.travel.admin.service.hotel;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.HotelMapper;
import com.zheng.orm.travel.mapper.HotelTypeMapper;
import com.zheng.orm.travel.mapper.HotelTypeMiddleMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.collection.CollectionUtils;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.Hotel;
import com.zheng.travel.admin.pojo.HotelTypeMiddle;
import com.zheng.travel.admin.vo.HotelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {


    @Resource
    private HotelTypeMapper hotelTypeMapper;
    @Resource
    private HotelTypeMiddleMapper hotelTypeMiddleMapper;

    /**
     * 查询分页&搜索酒店管理
     */
    @Override
    public IPage<Hotel> findHotelPage(HotelVo hotelVo) {
        // 设置分页信息
        Page<Hotel> page = new Page<>(hotelVo.getPageNo(), hotelVo.getPageSize());
        // 设置查询条件
        LambdaQueryWrapper<Hotel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Hotel.class, column -> !column.getColumn().equals("hotel_service_item"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(hotelVo.getKeyword()), Hotel::getTitle, hotelVo.getKeyword());
        //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(hotelVo.getStatus() != null, Hotel::getStatus, hotelVo.getStatus());
        lambdaQueryWrapper.eq(hotelVo.getIsDelete() != null, Hotel::getIsdelete, hotelVo.getIsDelete());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(hotelVo.getKeyword()),wrapper -> wrapper
        //         .like(Hotel::getName, hotelVo.getKeyword())
        //         .or()
        //         .like(Hotel::getCategoryName, hotelVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(Hotel::getCreateTime);
        // 查询分页返回
        IPage<Hotel> results = this.page(page, lambdaQueryWrapper);
        return results;
    }

    /**
     * 查询酒店管理列表信息
     */
    @Override
    public List<Hotel> findHotelList() {
        // 1：设置查询条件
        LambdaQueryWrapper<Hotel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(Hotel::getStatus, 1);
        lambdaQueryWrapper.eq(Hotel::getIsdelete, 0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

    /**
     * 根据id查询酒店管理明细信息
     */
    @Override
    public Hotel getHotelById(Long id) {
        Hotel hotel = this.getById(id);
        LambdaQueryWrapper<HotelTypeMiddle> hotelTypeMiddleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hotelTypeMiddleLambdaQueryWrapper.eq(HotelTypeMiddle::getHotelId,id);
        List<HotelTypeMiddle> hotelTypeMiddles = hotelTypeMiddleMapper.selectList(hotelTypeMiddleLambdaQueryWrapper);
        hotel.setHotelTypeMiddleList(hotelTypeMiddles);
        return hotel;
    }


    /**
     * 保存&修改酒店管理
     */
    @Override
    public Hotel saveupdateHotel(Hotel hotel) {
       try {
           boolean flag = this.saveOrUpdate(hotel);
           return flag ? hotel : null;
       } catch(Exception ex){
           ex.printStackTrace();
           return null;
       }
    }

    /**
     * 克隆酒店
     */
    @Override
    @Transactional
    public Hotel copyHotel(Hotel hotel){
        // 1: 对应的房型查询出来
        LambdaQueryWrapper<HotelTypeMiddle> hotelTypeMiddleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hotelTypeMiddleLambdaQueryWrapper.eq(HotelTypeMiddle::getHotelId,hotel.getId());
        List<HotelTypeMiddle> hotelTypeMiddles = hotelTypeMiddleMapper.selectList(hotelTypeMiddleLambdaQueryWrapper);
        // 2: 开始进行复制新的酒店
        hotel.setId(null);
        this.saveupdateHotel(hotel);
        // 3： 获取保存以后新的酒店的id
        Long newHotelId = hotel.getId();
        if(!CollectionUtils.isEmpty(hotelTypeMiddles)){
            for (HotelTypeMiddle hotelTypeMiddle : hotelTypeMiddles) {
                hotelTypeMiddle.setId(null);
                hotelTypeMiddle.setHotelId(newHotelId);
                this.hotelTypeMiddleMapper.insert(hotelTypeMiddle);
            }
        }
        return hotel;
    }


    /**
     * 根据id删除酒店管理
     */
    @Override
    public int deleteHotelById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchHotel(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<Hotel>
            List<Hotel> hotelList = Arrays.stream(strings).map(idstr -> {
                Hotel hotel = new Hotel();
                hotel.setId(new Long(idstr));
                hotel.setIsdelete(1);
                return hotel;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(hotelList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}