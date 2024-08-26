package com.zheng.travel.admin.service.role;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.vo.RoleVo;

import java.util.List;
import java.util.Map;

public interface IRoleService extends IService<Role> {


    /**
     * 查询角色对应的权限
     *
     * @param roleid
     * @return
     */
    List<Map<String, Object>> selectPermissionByRoleId(Long roleid);


    /**
     * 给用户授予对应得角色
     *
     * @param userId  用户id
     * @param roleIds 授予得角色列表
     */
    boolean grantUserRole(Long userId, Long[] roleIds);

    /**
     * 查询角色管理列表信息
     */
    List<Role> findRoleList();

    /**
     * 查询角色管理列表信息并分页
     */
    IPage<Role> findRolePage(RoleVo roleVo);

    /**
     * 保存&修改角色管理
     */
    Role saveupdateRole(Role role);

    /**
     * 根据Id删除角色管理
     */
    int deleteRoleById(Long id);

    /**
     * 根据Id查询角色管理明细信息
     */
    Role getRoleById(Long id);

    /**
     * 根据角色管理ids批量删除角色管理
     */
    boolean delBatchRole(String ids);

    /**
     * 解除用户的角色绑定
     *
     * @param userId
     * @param roleIds
     * @return
     */
    boolean removeGrantUserRole(Long userId, Long[] roleIds);

    /**
     * 用户角色权限授权
     * @param roleId
     * @param permissionIds
     * @return
     */
    boolean grantRolePermission(Long roleId, Long[] permissionIds);
}