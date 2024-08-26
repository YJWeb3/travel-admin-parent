package com.zheng.orm.travel.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.pojo.SysLoginUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLoginUserMapper extends BaseMapper<SysLoginUser> {
    // 查询用户对应的角色
    List<Role> findSysRoleByUserId(@Param("userId")Long userid);
    // 查询用户对应角色的权限列表
    List<Permission> findBySysPermissionUserId(@Param("userId") Long userId);
    // 多表关联查询分页
    IPage<SysLoginUser> findLoginUserPage(Page<SysLoginUser> page, @Param("ew") Wrapper<SysLoginUser> queryWrapper);
}
