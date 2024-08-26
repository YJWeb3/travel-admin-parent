package com.zheng.travel.admin.service.hoteltypemiddle;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.HotelTypeMiddleMapper;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.commons.utils.json.JsonUtil;
import com.zheng.travel.admin.pojo.HotelType;
import com.zheng.travel.admin.pojo.HotelTypeMiddle;
import com.zheng.travel.admin.service.hoteltype.IHotelTypeService;
import com.zheng.travel.admin.vo.HotelTypeMiddleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class HotelTypeMiddleServiceImpl extends ServiceImpl<HotelTypeMiddleMapper, HotelTypeMiddle>
        implements IHotelTypeMiddleService {

    @Autowired
    private IHotelTypeService hotelTypeService;

    @Override
    @Transactional
    public List<HotelTypeMiddle> findHotelTypeInfos(HotelTypeMiddleVo hotelTypeMiddleVo) {
        // 转换用户传递过来的数据
        List<HotelTypeMiddle> hotelTypeMiddles = JsonUtil.string2Obj(hotelTypeMiddleVo.getHotelTypeDatas(), List.class, HotelTypeMiddle.class);
        for (HotelTypeMiddle hotelTypeMiddle : hotelTypeMiddles) {
            // 1 : 你可以根据名字进行查询看是否在房型表中存在，如果不存在就添加再关联，
            // 查询当前酒店和房型是否绑定过。如果绑定过了就不处理了
            if (hotelTypeMiddle.getId() == null) {
                // 1 : 你可以根据名字进行查询看是否在房型表中存在，如果不存在就添加再关联，
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
                // 在中见表创建房型
                this.saveOrUpdate(hotelTypeMiddle);
            }else{
                //存在做更新
                this.saveOrUpdate(hotelTypeMiddle);
            }
        }

        return hotelTypeMiddles;
    }
}
