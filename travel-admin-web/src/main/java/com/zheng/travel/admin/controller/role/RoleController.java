package com.zheng.travel.admin.controller.role;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.orm.travel.mapper.SysRoleUserMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.pojo.RoleUser;
import com.zheng.travel.admin.service.role.IRoleService;
import com.zheng.travel.admin.vo.GrantRoleUserVo;
import com.zheng.travel.admin.vo.RoleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class RoleController extends BaseController {

    private final IRoleService roleService;
    private final SysRoleUserMapper sysRoleUserMapper;


    /**
     * 查询角色查询对应的权限
     *
     * @param roleid
     * @return
     */
    @PostMapping("/role/permission/{roleid}")
    public List<Map<String, Object>> removeGrantUserRole(@PathVariable("roleid") Long roleid) {
        List<Map<String, Object>> mapList = roleService.selectPermissionByRoleId(roleid);
        return mapList;
    }

    /**
     * 用户角色授权
     *
     * @param grantRoleUserVo
     * @return
     */
    @PostMapping("/role/user/grant/remove")
    public boolean removeGrantUserRole(@RequestBody GrantRoleUserVo grantRoleUserVo) {
        return roleService.removeGrantUserRole(grantRoleUserVo.getUserId(), grantRoleUserVo.getRoleIds());
    }

    /**
     * 用户角色授权
     *
     * @param grantRoleUserVo
     * @return
     */
    @PostMapping("/role/user/grant")
    public boolean grantUserRole(@RequestBody GrantRoleUserVo grantRoleUserVo) {
        return roleService.grantUserRole(grantRoleUserVo.getUserId(), grantRoleUserVo.getRoleIds());
    }

    /**
     * 用户角色授权
     *
     * @param grantRoleUserVo
     * @return
     */
    @PostMapping("/role/permission/grant")
    public boolean grantRolePermission(@RequestBody GrantRoleUserVo grantRoleUserVo) {
        return roleService.grantRolePermission(grantRoleUserVo.getRoleId(), grantRoleUserVo.getPermissionIds());
    }

    /**
     * 查询角色管理列表信息
     *
     * @return
     * @path : /admin/role/load
     * @method: findRoles
     * @result : List<Role>
     * 创建人:yykk
     * 创建时间：2022-04-18 01:10:19
     * @version 1.0.0
     */
    @PostMapping("/role/load")
    public List<Role> findRoleList(@RequestBody RoleVo roleVo) {
        List<Role> roleList = roleService.findRoleList();
        ;
        if (CollectionUtils.isNotEmpty(roleList) && roleVo.getUserId() != null) {

            // 1: 获取用户已经授权的角色
            LambdaQueryWrapper<RoleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RoleUser::getUserId, roleVo.getUserId());
            // 3: 查询用户和某个角色id是否已经授权
            List<RoleUser> roleUsers = sysRoleUserMapper.selectList(lambdaQueryWrapper);
            // 4: 这个判断一定要加。因为如果用户没授权过，后面代码执行意义就没有
            if (CollectionUtils.isNotEmpty(roleUsers)) {
                // 获取到该用户userid已经授权的角色ID列表
                List<Long> roleIds = roleUsers.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
                // 开设把用户授权过的角色id和roleList进行碰撞，如果又交集说明说明授权，如果没有交集说明没有授权过
                roleList = roleList.stream().map(role -> {
                    // 如果当前的roleid在用户授权的角色列表中，就返回 count 如果count == 0 代表没授权，反之授权
                    long count = roleIds.stream().filter(roleid -> roleid.equals(role.getId())).count();
                    boolean isGrant = count == 0;
                    role.setIsGrant(!isGrant);
                    return role;
                }).collect(Collectors.toList());
            }
        }
        return roleList;
    }

    /**
     * 查询角色管理列表信息并分页
     */
    @PostMapping("/role/list")
    public IPage<Role> findRoles(@RequestBody RoleVo roleVo) {
        IPage<Role> rolePage = roleService.findRolePage(roleVo);
        // roleList是所有的角色列表
        List<Role> roleList = rolePage.getRecords();
        if (CollectionUtils.isNotEmpty(roleList) && roleVo.getUserId() != null) {
            // 1: 获取用户已经授权的角色
            LambdaQueryWrapper<RoleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RoleUser::getUserId, roleVo.getUserId());
            // 3: 查询用户和某个角色id是否已经授权
            List<RoleUser> roleUsers = sysRoleUserMapper.selectList(lambdaQueryWrapper);
            // 4: 这个判断一定要加。因为如果用户没授权过，后面代码执行意义就没有
            if (CollectionUtils.isNotEmpty(roleUsers)) {
                // 获取到该用户userid已经授权的角色ID列表
                List<Long> roleIds = roleUsers.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
                // 开设把用户授权过的角色id和roleList进行碰撞，如果又交集说明说明授权，如果没有交集说明没有授权过
                roleList = roleList.stream().map(role -> {
                    // 如果当前的roleid在用户授权的角色列表中，就返回 count 如果count == 0 代表没授权，反之授权
                    long count = roleIds.stream().filter(roleid -> roleid.equals(role.getId())).count();
                    boolean isGrant = count == 0;
                    role.setIsGrant(!isGrant);
                    return role;
                }).collect(Collectors.toList());

                rolePage.setRecords(roleList);
            }
        }

        return rolePage;


    }


    /**
     * 根据角色管理id查询明细信息
     */
    @GetMapping("/role/get/{id}")
    public Role getRoleById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return roleService.getRoleById(new Long(id));
    }


    /**
     * 保存和修改角色管理
     */
    @PostMapping("/role/saveupdate")
    public Role saveupdateRole(@RequestBody Role role) {
        return roleService.saveupdateRole(role);
    }


    /**
     * 根据角色管理id删除角色管理
     *
     * @param : id
     * @method: delete/{id}
     * @path : /admin/role/delete/{id}
     * @result : int
     * 创建人:yykk
     * 创建时间：2022-04-18 01:10:19
     * @version 1.0.0
     */
    @PostMapping("/role/delete/{id}")
    public int deleteRoleById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return roleService.deleteRoleById(new Long(id));
    }


    /**
     * 根据角色管理ids批量删除角色管理
     */
    @PostMapping("/role/delBatch")
    public boolean delRole(@RequestBody RoleVo roleVo) {
        log.info("你要批量删除的IDS是:{}", roleVo.getBatchIds());
        if (Vsserts.isEmpty(roleVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return roleService.delBatchRole(roleVo.getBatchIds());
    }

}