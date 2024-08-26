package com.zheng.travel.admin.service.indexcategory;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.IndexCategoryMapper;
import com.zheng.travel.admin.pojo.IndexCategory;
import com.zheng.travel.admin.vo.IndexCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class IndexCategoryServiceImpl extends ServiceImpl<IndexCategoryMapper,IndexCategory> implements IIndexCategoryService  {

    /**
     * 查询分页&搜索首页分类
     */
    @Override
	public IPage<IndexCategory> findIndexCategoryPage(IndexCategoryVo indexcategoryVo){
	    // 设置分页信息
		Page<IndexCategory> page = new Page<>(indexcategoryVo.getPageNo(),indexcategoryVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<IndexCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(IndexCategory.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(indexcategoryVo.getKeyword()), IndexCategory::getTitle,indexcategoryVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(indexcategoryVo.getStatus() != null ,IndexCategory::getStatus,indexcategoryVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(indexcategoryVo.getKeyword()),wrapper -> wrapper
        //         .like(IndexCategory::getName, indexcategoryVo.getKeyword())
        //         .or()
        //         .like(IndexCategory::getCategoryName, indexcategoryVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(IndexCategory::getCreateTime);
        // 查询分页返回
		IPage<IndexCategory> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询首页分类列表信息
    */
    @Override
    public List<IndexCategory> findIndexCategoryList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<IndexCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(IndexCategory::getStatus,1);
        lambdaQueryWrapper.eq(IndexCategory::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询首页分类明细信息
     */
    @Override
    public IndexCategory getIndexCategoryById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改首页分类
     */
    @Override
	public IndexCategory saveupdateIndexCategory(IndexCategory indexcategory){
		boolean flag = this.saveOrUpdate(indexcategory);
		return flag ? indexcategory : null;
	}


    /**
     * 根据id删除首页分类
     */
    @Override
    public int deleteIndexCategoryById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchIndexCategory(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<IndexCategory>
            List<IndexCategory> indexcategoryList = Arrays.stream(strings).map(idstr -> {
                IndexCategory indexcategory = new IndexCategory();
                indexcategory.setId(new Long(idstr));
                indexcategory.setIsdelete(1);
                return indexcategory;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(indexcategoryList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}