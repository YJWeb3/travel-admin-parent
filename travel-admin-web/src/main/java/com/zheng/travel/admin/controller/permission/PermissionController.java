package com.zheng.travel.admin.controller.permission;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.permission.IPermissionService;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.vo.PermissionVo;
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
public class PermissionController extends BaseController{

    private final IPermissionService permissionService;


    /**
     * 查询权限列表信息
     */
    @GetMapping("/permission/load")
    public List<Permission> findPermissionList() {
        return permissionService.findPermissionList();
    }

	/**
	 * 查询权限列表信息并分页
	*/
    @PostMapping("/permission/list")
    public IPage<Permission> findPermissions(@RequestBody PermissionVo permissionVo) {
        return permissionService.findPermissionPage(permissionVo);
    }


    /**
     * 根据权限id查询明细信息
    */
    @GetMapping("/permission/get/{id}")
    public Permission getPermissionById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return permissionService.getPermissionById(new Long(id));
    }


	/**
	 * 保存和修改权限
	*/
    @PostMapping("/permission/saveupdate")
    public Permission saveupdatePermission(@RequestBody Permission permission) {
        return permissionService.saveupdatePermission(permission);
    }


    /**
	 * 根据权限id删除权限
	*/
    @PostMapping("/permission/delete/{id}")
    public int deletePermissionById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return permissionService.deletePermissionById(new Long(id));
    }


   /**
   	 * 根据权限ids批量删除权限
   	*/
    @PostMapping("/permission/delBatch")
    public boolean delPermission(@RequestBody PermissionVo permissionVo) {
        log.info("你要批量删除的IDS是:{}", permissionVo.getBatchIds());
        if (Vsserts.isEmpty(permissionVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return permissionService.delBatchPermission(permissionVo.getBatchIds());
    }

}