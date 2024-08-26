package com.zheng.travel.admin.controller.sysloginuser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.threadlocal.UserThrealLocal;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.service.sysloginuser.ISysLoginUserService;
import com.zheng.travel.admin.vo.SysLoginUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SysLoginUserController extends BaseController{

    private final ISysLoginUserService sysloginuserService;



    @PostMapping("/user/permission")
    public List<Permission> findUserPermission() {
        SysLoginUser sysLoginUser = UserThrealLocal.get();
        List<Permission> permissionList = sysloginuserService.findBySysPermissionUserId(sysLoginUser.getId());
        return permissionList;
    }


    /**
     * 查询用户管理列表信息
     */
    @GetMapping("/sysloginuser/load")
    public List<SysLoginUser> findSysLoginUserList() {
        return sysloginuserService.findSysLoginUserList();
    }

	/**
	 * 查询用户管理列表信息并分页
	*/
    @PostMapping("/sysloginuser/list")
    public IPage<SysLoginUser> findSysLoginUsers(@RequestBody SysLoginUserVo sysloginuserVo) {
        return sysloginuserService.findSysLoginUserPage(sysloginuserVo);
    }


    /**
     * 根据用户管理id查询明细信息
    */
    @GetMapping("/sysloginuser/get/{id}")
    public SysLoginUser getSysLoginUserById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
           throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return sysloginuserService.getSysLoginUserById(new Long(id));
    }


	/**
	 * 保存和修改用户管理
	*/
    @PostMapping("/sysloginuser/saveupdate")
    public SysLoginUser saveupdateSysLoginUser(@RequestBody SysLoginUser sysloginuser) {
        return sysloginuserService.saveupdateSysLoginUser(sysloginuser);
    }


    /**
	 * 根据用户管理id删除用户管理
	*/
    @PostMapping("/sysloginuser/delete/{id}")
    public int deleteSysLoginUserById(@PathVariable("id") String id) {
        if(Vsserts.isEmpty(id)){
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return sysloginuserService.deleteSysLoginUserById(new Long(id));
    }


   /**
   	 * 根据用户管理ids批量删除用户管理
   	*/
    @PostMapping("/sysloginuser/delBatch")
    public boolean delSysLoginUser(@RequestBody SysLoginUserVo sysloginuserVo) {
        log.info("你要批量删除的IDS是:{}", sysloginuserVo.getBatchIds());
        if (Vsserts.isEmpty(sysloginuserVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return sysloginuserService.delBatchSysLoginUser(sysloginuserVo.getBatchIds());
    }

}