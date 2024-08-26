package com.zheng.travel.admin.service.sysloginuser;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zheng.orm.travel.mapper.SysLoginUserMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.commons.utils.pwd.DesUtils;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.Role;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.vo.SysLoginUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SysLoginUserServiceImpl extends ServiceImpl<SysLoginUserMapper,SysLoginUser> implements ISysLoginUserService  {


    @Override
    public List<Role> findSysRoleByUserId(Long userid) {
        return this.baseMapper.findSysRoleByUserId(userid);
    }

    @Override
    public List<Permission> findBySysPermissionUserId(Long userid) {
        try {
            List<Permission> permissionList = this.baseMapper.findBySysPermissionUserId(userid);
            if (CollectionUtils.isNotEmpty(permissionList)) {
                // distinct() 你的hashcode必须相同和eqauls必须是true
                permissionList = permissionList.stream().peek(p -> {
                    p.setCode(DesUtils.encrypt(p.getCode()));
                    p.setUrl(DesUtils.encrypt(p.getUrl()));
                }).distinct().collect(Collectors.toList());
                return permissionList;
            } else {
                return Lists.newArrayList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Lists.newArrayList();
        }
    }

    /**
     * 查询分页&搜索用户管理
     */
    @Override
	public IPage<SysLoginUser> findSysLoginUserPage(SysLoginUserVo sysloginuserVo){
	    // 设置分页信息
		Page<SysLoginUser> page = new Page<>(sysloginuserVo.getPageNo(),sysloginuserVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<SysLoginUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(SysLoginUser.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(sysloginuserVo.getKeyword()), SysLoginUser::getNickname,sysloginuserVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(sysloginuserVo.getStatus() != null ,SysLoginUser::getStatus,sysloginuserVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(sysloginuserVo.getKeyword()),wrapper -> wrapper
        //         .like(SysLoginUser::getName, sysloginuserVo.getKeyword())
        //         .or()
        //         .like(SysLoginUser::getCategoryName, sysloginuserVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(SysLoginUser::getCreateTime);
        // 查询分页返回
		IPage<SysLoginUser> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询用户管理列表信息
    */
    @Override
    public List<SysLoginUser> findSysLoginUserList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<SysLoginUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(SysLoginUser::getStatus,1);
        lambdaQueryWrapper.eq(SysLoginUser::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询用户管理明细信息
     */
    @Override
    public SysLoginUser getSysLoginUserById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改用户管理
     */
    @Override
	public SysLoginUser saveupdateSysLoginUser(SysLoginUser sysloginuser){
		boolean flag = this.saveOrUpdate(sysloginuser);
		return flag ? sysloginuser : null;
	}


    /**
     * 根据id删除用户管理
     */
    @Override
    public int deleteSysLoginUserById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchSysLoginUser(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<SysLoginUser>
            List<SysLoginUser> sysloginuserList = Arrays.stream(strings).map(idstr -> {
                SysLoginUser sysloginuser = new SysLoginUser();
                sysloginuser.setId(new Long(idstr));
                sysloginuser.setIsdelete(1);
                return sysloginuser;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(sysloginuserList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}