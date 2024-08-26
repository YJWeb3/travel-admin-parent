package com.zheng.travel.admin.service.pca;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.Pca;

import java.util.List;

public interface IPcaService extends IService<Pca> {

    /**
     * 查询省市区的数据
     *
     * @return
     */
    List<Pca> findPcaInfo();
}
