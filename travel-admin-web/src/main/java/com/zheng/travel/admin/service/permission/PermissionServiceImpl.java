package com.zheng.travel.admin.service.permission;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.PermissionMapper;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.vo.PermissionVo;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper,Permission> implements IPermissionService  {

    /**
     * 查询分页&搜索权限
     */
    @Override
	public IPage<Permission> findPermissionPage(PermissionVo permissionVo){
	    // 设置分页信息
		Page<Permission> page = new Page<>(permissionVo.getPageNo(),permissionVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(Permission.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(permissionVo.getKeyword()), Permission::getName,permissionVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(permissionVo.getStatus() != null ,Permission::getStatus,permissionVo.getStatus());
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(permissionVo.getKeyword()), Permission::getPathname,permissionVo.getKeyword());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(permissionVo.getKeyword()),wrapper -> wrapper
        //         .like(Permission::getName, permissionVo.getKeyword())
        //         .or()
        //         .like(Permission::getCategoryName, permissionVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(Permission::getCreateTime);
        // 查询分页返回
		IPage<Permission> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询权限列表信息
    */
    @Override
    public List<Permission> findPermissionList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(Permission::getStatus,1);
        lambdaQueryWrapper.eq(Permission::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询权限明细信息
     */
    @Override
    public Permission getPermissionById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改权限
     */
    @Override
	public Permission saveupdatePermission(Permission permission){
		boolean flag = this.saveOrUpdate(permission);
		return flag ? permission : null;
	}


    /**
     * 根据id删除权限
     */
    @Override
    public int deletePermissionById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchPermission(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<Permission>
            List<Permission> permissionList = Arrays.stream(strings).map(idstr -> {
                Permission permission = new Permission();
                permission.setId(new Long(idstr));
                permission.setIsdelete(1);
                return permission;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(permissionList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}