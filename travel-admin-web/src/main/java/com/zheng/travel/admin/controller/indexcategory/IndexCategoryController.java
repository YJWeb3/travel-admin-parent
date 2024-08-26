package com.zheng.travel.admin.controller.indexcategory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.indexcategory.IIndexCategoryService;
import com.zheng.travel.admin.pojo.IndexCategory;
import com.zheng.travel.admin.vo.IndexCategoryVo;
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
public class IndexCategoryController extends BaseController{

    private final IIndexCategoryService indexcategoryService;


    /**
     * 查询首页分类列表信息
     */
    @GetMapping("/indexcategory/load")
    public List<IndexCategory> findIndexCategoryList() {
        return indexcategoryService.findIndexCategoryList();
    }

	/**
	 * 查询首页分类列表信息并分页
	*/
    @PostMapping("/indexcategory/list")
    public IPage<IndexCategory> findIndexCategorys(@RequestBody IndexCategoryVo indexcategoryVo) {
        return indexcategoryService.findIndexCategoryPage(indexcategoryVo);
    }


    /**
     * 根据首页分类id查询明细信息
    */
    @GetMapping("/indexcategory/get/{id}")
    public IndexCategory getIndexCategoryById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return indexcategoryService.getIndexCategoryById(new Long(id));
    }


	/**
	 * 保存和修改首页分类
	*/
    @PostMapping("/indexcategory/saveupdate")
    public IndexCategory saveupdateIndexCategory(@RequestBody IndexCategory indexcategory) {
        return indexcategoryService.saveupdateIndexCategory(indexcategory);
    }


    /**
	 * 根据首页分类id删除首页分类
	*/
    @PostMapping("/indexcategory/delete/{id}")
    public int deleteIndexCategoryById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return indexcategoryService.deleteIndexCategoryById(new Long(id));
    }


   /**
   	 * 根据首页分类ids批量删除首页分类
   	*/
    @PostMapping("/indexcategory/delBatch")
    public boolean delIndexCategory(@RequestBody IndexCategoryVo indexcategoryVo) {
        log.info("你要批量删除的IDS是:{}", indexcategoryVo.getBatchIds());
        if (Vsserts.isEmpty(indexcategoryVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return indexcategoryService.delBatchIndexCategory(indexcategoryVo.getBatchIds());
    }

}