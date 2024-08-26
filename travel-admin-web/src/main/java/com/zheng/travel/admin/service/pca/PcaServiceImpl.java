package com.zheng.travel.admin.service.pca;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.PcaMapper;
import com.zheng.travel.admin.pojo.Pca;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PcaServiceImpl extends ServiceImpl<PcaMapper, Pca> implements IPcaService{


    /**
     * 根据pid对应的省市区信息
     * @param pid
     * @return
     */
    public List<Pca> findPacMain(Integer pid){
        LambdaQueryWrapper<Pca> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Pca::getPid,pid);
        return this.list(lambdaQueryWrapper);
    }

    /**
     * 查询省市区的数据
     * @return
     */
    public List<Pca> findPcaInfo(){
        // 1: 查询第一级分类
        List<Pca> provinceDatas = findPacMain(0);
        provinceDatas.forEach(pca -> {
            List<Pca> cityDatas = findPacMain(pca.getId());
            cityDatas.forEach(city->{
                List<Pca> areaDatas = findPacMain(city.getId());
                city.setChildrens(areaDatas);
            });
            pca.setChildrens(cityDatas);
        });
        return provinceDatas;
    }

}
