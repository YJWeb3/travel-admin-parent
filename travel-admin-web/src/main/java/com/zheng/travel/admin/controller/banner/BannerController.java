package com.zheng.travel.admin.controller.banner;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.banner.IBannerService;
import com.zheng.travel.admin.pojo.Banner;
import com.zheng.travel.admin.vo.BannerVo;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.zheng.travel.admin.controller.BaseController;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BannerController extends BaseController{

    private final IBannerService bannerService;


    /**
     * 查询轮播图管理列表信息
     */
    @GetMapping("/banner/load")
    public List<Banner> findBannerList() {
        return bannerService.findBannerList();
    }

	/**
	 * 查询轮播图管理列表信息并分页
	*/
    @PostMapping("/banner/list")
    public IPage<Banner> findBanners(@RequestBody BannerVo bannerVo) {
        return bannerService.findBannerPage(bannerVo);
    }


    /**
     * 根据轮播图管理id查询明细信息
    */
    @GetMapping("/banner/get/{id}")
    public Banner getBannerById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return bannerService.getBannerById(new Long(id));
    }


	/**
	 * 保存和修改轮播图管理
	*/
    @PostMapping("/banner/saveupdate")
    public Banner saveupdateBanner(@RequestBody Banner banner) {
        return bannerService.saveupdateBanner(banner);
    }


    /**
	 * 根据轮播图管理id删除轮播图管理
	*/
    @PostMapping("/banner/delete/{id}")
    public int deleteBannerById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return bannerService.deleteBannerById(new Long(id));
    }


   /**
   	 * 根据轮播图管理ids批量删除轮播图管理
   	*/
    @PostMapping("/banner/delBatch")
    public boolean delBanner(@RequestBody BannerVo bannerVo) {
        log.info("你要批量删除的IDS是:{}", bannerVo.getBatchIds());
        if (Vsserts.isEmpty(bannerVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return bannerService.delBatchBanner(bannerVo.getBatchIds());
    }

}