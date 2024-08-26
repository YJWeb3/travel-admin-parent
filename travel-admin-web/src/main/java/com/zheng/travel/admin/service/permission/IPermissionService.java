package com.zheng.travel.admin.service.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.vo.PermissionVo;
import java.util.List;


public interface IPermissionService extends IService<Permission>{


    /**
     * 查询权限列表信息
     */
    List<Permission> findPermissionList() ;

	/**
     * 查询权限列表信息并分页
    */
	IPage<Permission> findPermissionPage(PermissionVo permissionVo);

    /**
     * 保存&修改权限
    */
    Permission saveupdatePermission(Permission permission);

    /**
     * 根据Id删除权限
     */
    int deletePermissionById(Long id) ;

    /**
     * 根据Id查询权限明细信息
    */
    Permission getPermissionById(Long id);

    /**
     * 根据权限ids批量删除权限
    */
    boolean delBatchPermission(String ids);

}