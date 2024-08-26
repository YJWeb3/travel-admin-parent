package com.zheng.travel.admin.controller.pca;

import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.Pca;
import com.zheng.travel.admin.service.pca.IPcaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PcaController extends BaseController {

    @Autowired
    private IPcaService pcaService;


    /**
     * 查询省市区的数据
     *
     * @return
     */
    @GetMapping("/pca/load")
    public List<Pca> findPcaInfo(){
        return pcaService.findPcaInfo();
    }


    /**
     * 查询省市区的数据
     *
     * @return
     */
    @GetMapping("/pca/load2")
    public List<Pca> findPcaInfo2(){
        return pcaService.list();
    }


}
