package com.zheng.travel.admin.service.role;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.PermissionMapper;
import com.zheng.orm.travel.mapper.RoleMapper;
import com.zheng.orm.travel.mapper.SysRolePermissionMapper;
import com.zheng.orm.travel.mapper.SysRoleUserMapper;
import com.zheng.travel.admin.commons.anno.TravelFeild;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.Tool;
import com.zheng.travel.admin.commons.utils.collection.CollectionUtils;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.pojo.RolePermission;
import com.zheng.travel.admin.pojo.RoleUser;
import com.zheng.travel.admin.vo.RoleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final SysRoleUserMapper sysRoleUserMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final PermissionMapper permissionMapper;


    /**
     * 权限控制
     *
     * @param pid
     * @return
     */
    public List<Permission> selectPermission(Long pid) {
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Permission::getPid, pid);
        lambdaQueryWrapper.orderByAsc(Permission::getSorted);
        List<Permission> sysPermissions = permissionMapper.selectList(lambdaQueryWrapper);
        return sysPermissions;
    }

    /**
     * 查询角色对应的权限
     *
     * @param roleid
     * @return
     */
    @Override
    public List<Map<String, Object>> selectPermissionByRoleId(Long roleid) {
        // 1: 查询角色对应的权限
        List<Long> permissionIds = this.baseMapper.selectRolePermission(roleid);
        // 2: 查询根级别的权限
        List<Permission> rootPermissions = selectPermission(0L);
        List<Map<String, Object>> mapList = rootPermissions.stream().map(root -> {
            // 判断这个角色对应权限是否已经授予。如果已经授予就把状态设置checked =  true
            boolean checked = permissionIds.stream().filter(p -> p.equals(root.getId())).count() > 0;
            Map<String, Object> map = new HashMap<>();
            map.put("id", root.getId());
            map.put("name", root.getName());
            map.put("pid", 0);
            map.put("expand", false);
            map.put("checked", checked);
            bubblePermission(root, map, permissionIds);
            return map;
        }).collect(Collectors.toList());
        return mapList;
    }


    public void bubblePermission(Permission root, Map<String, Object> map, List<Long> permissionIds) {
        // 查询对应的子权限
        List<Permission> childrenList = selectPermission(root.getId());
        if (!CollectionUtils.isEmpty(childrenList)) {
            List<Map<String, Object>> childrenMap = childrenList.stream().map(cp -> {
                Map<String, Object> map2 = new HashMap<>();
                boolean checked2 = permissionIds.stream().filter(p -> p.equals(cp.getId())).count() > 0;
                map2.put("id", cp.getId());
                map2.put("name", cp.getName());
                map2.put("pid", root.getId());
                map2.put("expand", false);
                map2.put("checked", checked2);
                bubblePermission(cp, map2, permissionIds);
                return map2;
            }).collect(Collectors.toList());
            map.put("children", childrenMap);
        } else {
            map.put("children", new ArrayList<>());
        }
    }

    /**
     * 解除用户的角色绑定
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public boolean removeGrantUserRole(Long userId, Long[] roleIds) {
        // 1: 根据用户id查询用户是否授予权限
        // 代表都已经授权过了 ，前台提示：你已经授权过了
        boolean flag = false;
        // 2: 开设循环授权角色列表
        for (Long roleId : roleIds) {
            LambdaQueryWrapper<RoleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RoleUser::getUserId, userId);
            lambdaQueryWrapper.eq(RoleUser::getRoleId, roleId);
            // 3: 查询用户和某个角色id是否已经授权
            RoleUser roleUser = sysRoleUserMapper.selectOne(lambdaQueryWrapper);
            // 4: 如果没有授权，直接添加
            if (Vsserts.isNotNull(roleUser)) {
                // 添加注册到数据库表中
                sysRoleUserMapper.deleteById(roleUser);
                // true 代表存在授权，前台：提示授权成功
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 给用户授予对应得角色
     *
     * @param roleId        用户id
     * @param permissionIds 授予得角色列表
     */
    @Override
    @Transactional
    public boolean grantRolePermission(Long roleId, Long[] permissionIds) {
        // 删除之前角色对应的权限
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermission::getRoleId, roleId);
        sysRolePermissionMapper.delete(lambdaQueryWrapper);
        // 开始添加权限
        boolean flag = false;
        // 2: 开设循环授权角色列表
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(roleId);
            // 添加注册到数据库表中
            sysRolePermissionMapper.insert(rolePermission);
            // true 代表存在授权，前台：提示授权成功
            flag = true;
        }

        return flag;
    }

    /**
     * 给用户授予对应得角色
     *
     * @param userId  用户id
     * @param roleIds 授予得角色列表
     */
    @Override
    public boolean grantUserRole(Long userId, Long[] roleIds) {
        // 1: 根据用户id查询用户是否授予权限
        // 代表都已经授权过了 ，前台提示：你已经授权过了
        boolean flag = false;
        // 2: 开设循环授权角色列表
        for (Long roleId : roleIds) {
            LambdaQueryWrapper<RoleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RoleUser::getUserId, userId);
            lambdaQueryWrapper.eq(RoleUser::getRoleId, roleId);
            // 3: 查询用户和某个角色id是否已经授权
            Long count = sysRoleUserMapper.selectCount(lambdaQueryWrapper).longValue();
            // 4: 如果没有授权，直接添加
            if (count == 0) {
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(roleId);
                // 添加注册到数据库表中
                sysRoleUserMapper.insert(roleUser);
                // true 代表存在授权，前台：提示授权成功
                flag = true;
            }
        }

        return flag;
    }


    /**
     * 查询分页&搜索角色管理
     */
    @Override
    public IPage<Role> findRolePage(RoleVo roleVo) {
        // 设置分页信息
        Page<Role> page = new Page<>(roleVo.getPageNo(), roleVo.getPageSize());
        // 设置查询条件
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(Role.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(roleVo.getKeyword()), Role::getName, roleVo.getKeyword());
        //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(roleVo.getStatus() != null, Role::getStatus, roleVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(roleVo.getKeyword()),wrapper -> wrapper
        //         .like(Role::getName, roleVo.getKeyword())
        //         .or()
        //         .like(Role::getCategoryName, roleVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(Role::getCreateTime);
        // 查询分页返回
        IPage<Role> results = this.page(page, lambdaQueryWrapper);
        return results;
    }

    /**
     * 查询角色管理列表信息
     */
    @Override
    public List<Role> findRoleList() {
        // 1：设置查询条件
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(Role::getStatus, 1);
        lambdaQueryWrapper.eq(Role::getIsdelete, 0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

    /**
     * 根据id查询角色管理明细信息
     */
    @Override
    public Role getRoleById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改角色管理
     */
    @Override
    public Role saveupdateRole(Role role) {
        boolean flag = this.saveOrUpdate(role);
        return flag ? role : null;
    }


    /**
     * 根据id删除角色管理
     */
    @Override
    public int deleteRoleById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchRole(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<Role>
            List<Role> roleList = Arrays.stream(strings).map(idstr -> {
                Role role = new Role();
                role.setId(new Long(idstr));
                role.setIsdelete(1);
                return role;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(roleList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }

    public static <T> List<String> getFilterFields(Class<T> clz, List<String> filterField) {
        List<String> list = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            list.add(Tool.humpToLine(field.getName()));
        }
        List<String> collect = list.stream().filter(field -> filterField.stream()
                .filter(f -> f.equals(field)).count() == 0).collect(Collectors.toList());

        return collect;
    }

    public static <T> List<String> getFilterFieldsAnnotaion(Class<T> clz, List<String> filterField) {
        List<String> list = new ArrayList<>();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            long count = filterField.stream().filter(f -> f.equals(field.getName())).count();
            TravelFeild annotation = field.getAnnotation(TravelFeild.class);
            if(annotation!=null && count == 0){
                list.add(annotation.value());
            }
        }
        return list;
    }


//    public QueryWrapper<Permission> buildQueryWrapper(Long roleId){
//        List<String> dbRoleFilterFields = dbService.getByRoleid(roleId);
//        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
//        if(CollectionUtils.isEmpty(dbRoleFilterFields)){
//            List<String> filterFields = getFilterFields(Permission.class,dbRoleFilterFields);
//            queryWrapper.select(filterFields.toString());//
//            return queryWrapper;
//        }
//        return queryWrapper;
//    }

    public static void main(String[] args) {
        // 根据角色id查询出对应的过滤字段
        List<String> filterField = new ArrayList<>();
        filterField.add("create_time");
        filterField.add("update_time");
        filterField.add("isdelete");

        List<String> filterFields = getFilterFields(Permission.class, filterField);
        filterFields.forEach(System.out::println);

        List<String> headerNames = getFilterFieldsAnnotaion(Permission.class, filterField);
        filterFields.forEach(System.out::println);
        headerNames.forEach(System.out::println);
    }


}