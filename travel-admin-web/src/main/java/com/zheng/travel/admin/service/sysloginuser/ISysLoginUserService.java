package com.zheng.travel.admin.service.sysloginuser;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.vo.SysLoginUserVo;

import java.util.List;

public interface ISysLoginUserService extends IService<SysLoginUser>{

    /**
     * 根据用户查询对应的角色
     * @param userid
     * @return
     */
    List<Role> findSysRoleByUserId(Long userid);

    /**
     * 查询用户对应的权限
     * @param userid
     * @return
     */
    List<Permission> findBySysPermissionUserId(Long userid);


    /**
     * 查询用户管理列表信息
     */
    List<SysLoginUser> findSysLoginUserList() ;

	/**
     * 查询用户管理列表信息并分页
    */
	IPage<SysLoginUser> findSysLoginUserPage(SysLoginUserVo sysloginuserVo);

    /**
     * 保存&修改用户管理
    */
    SysLoginUser saveupdateSysLoginUser(SysLoginUser sysloginuser);

    /**
     * 根据Id删除用户管理
     */
    int deleteSysLoginUserById(Long id) ;

    /**
     * 根据Id查询用户管理明细信息
    */
    SysLoginUser getSysLoginUserById(Long id);

    /**
     * 根据用户管理ids批量删除用户管理
    */
    boolean delBatchSysLoginUser(String ids);

}