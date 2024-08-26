package com.zheng.travel.admin.controller.hoteltypemiddle;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelBussinessException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.HotelType;
import com.zheng.travel.admin.pojo.HotelTypeMiddle;
import com.zheng.travel.admin.service.hoteltype.IHotelTypeService;
import com.zheng.travel.admin.service.hoteltypemiddle.IHotelTypeMiddleService;
import com.zheng.travel.admin.vo.HotelTypeMiddleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class HotelTypeMiddleController extends BaseController {


    @Autowired
    private IHotelTypeMiddleService hotelTypeMiddleService;
    @Autowired
    private IHotelTypeService hotelTypeService;

    /**
     * 实时添加 -- 返回具体中间的主键，后续就做更新处理即可
     *
     * @param hotelTypeMiddle
     * @return
     */
    @PostMapping("/hoteltype/middle/saveupdate")
    public HotelTypeMiddle saveHotelTypeMiddle(@RequestBody HotelTypeMiddle hotelTypeMiddle) {
        // 1 : 你可以根据名字进行查询看是否在房型表中存在，如果不存在就添加再关联，
        // 查询当前酒店和房型是否绑定过。如果绑定过了就不处理了
        if (hotelTypeMiddle.getId() == null) {
            // 1 : 你可以根据名字进行查询看是否在房型表中存在，如果不存在就添加再关联，
            if(Vsserts.isNotEmpty(hotelTypeMiddle.getTitle())){
                LambdaQueryWrapper<HotelType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(HotelType::getTitle, hotelTypeMiddle.getTitle());
                HotelType hotelType = hotelTypeService.getOne(lambdaQueryWrapper);
                if (Vsserts.isNull(hotelType)) {
                    hotelType = new HotelType();
                    hotelType.setTitle(hotelTypeMiddle.getTitle());
                    hotelType.setImg(hotelTypeMiddle.getImg());
                    hotelType.setDescription(hotelTypeMiddle.getDescription());
                    hotelType.setStatus(1);
                    hotelType.setIsdelete(0);
                    hotelType.setSorted(1);
                    hotelTypeService.saveOrUpdate(hotelType);
                }
                // 2: 你可以根据名字进行查询 如果存在就直接拿现有房型ID进行关联
                hotelTypeMiddle.setHotelTypeId(hotelType.getId());
            }
            // 在中见表创建房型
            hotelTypeMiddleService.saveOrUpdate(hotelTypeMiddle);
        }else{
            //存在做更新
            hotelTypeMiddleService.saveOrUpdate(hotelTypeMiddle);
        }

        return hotelTypeMiddle;
    }

    /**
     * 根据id删除对应酒店的房型
     * @return
     */
    @PostMapping("/hoteltype/middle/remove/{id}")
    public boolean removeHotelTypeMiddle(@PathVariable("id") Long id) {
        return hotelTypeMiddleService.removeById(id);
    }


    /**
     * 整体添加
     *
     * @param hotelTypeMiddleVo
     * @return
     */
    @PostMapping("/hoteltype/middle/saveupdate2")
    public List<HotelTypeMiddle> saveHotelTypeMiddle2(@RequestBody HotelTypeMiddleVo hotelTypeMiddleVo) {
        // 如果没有传递数据直接返回
        if(Vsserts.isEmpty(hotelTypeMiddleVo.getHotelTypeDatas())){
            throw new TravelBussinessException(ResultStatusEnumA.NULL_DATA);
        }
        return hotelTypeMiddleService.findHotelTypeInfos(hotelTypeMiddleVo);
    }

}
