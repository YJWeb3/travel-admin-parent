package com.zheng.travel.admin.service.indexcategory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.IndexCategory;
import com.zheng.travel.admin.vo.IndexCategoryVo;
import java.util.List;

public interface IIndexCategoryService extends IService<IndexCategory>{


    /**
     * 查询首页分类列表信息
     */
    List<IndexCategory> findIndexCategoryList() ;

	/**
     * 查询首页分类列表信息并分页
    */
	IPage<IndexCategory> findIndexCategoryPage(IndexCategoryVo indexcategoryVo);

    /**
     * 保存&修改首页分类
    */
    IndexCategory saveupdateIndexCategory(IndexCategory indexcategory);

    /**
     * 根据Id删除首页分类
     */
    int deleteIndexCategoryById(Long id) ;

    /**
     * 根据Id查询首页分类明细信息
    */
    IndexCategory getIndexCategoryById(Long id);

    /**
     * 根据首页分类ids批量删除首页分类
    */
    boolean delBatchIndexCategory(String ids);

}