package com.zheng.travel.admin.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zheng.orm.travel.mapper.SysLoginUserMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.commons.utils.pwd.DesUtils;
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
public class UserServiceImpl extends ServiceImpl<SysLoginUserMapper, SysLoginUser> implements IUserService {


    /**
     * 实现了多表关联查询分页
     */
    @Override
    public IPage<SysLoginUser> findLoginUserPage(int pageNo, int pageSize) {
        Page<SysLoginUser> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysLoginUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("su.status", 1);
        queryWrapper.eq("su.isdelete", 0);
        return this.baseMapper.findLoginUserPage(page, queryWrapper);
    }

    /**
     * 查询分页&搜索轮播图
     */
    @Override
    public IPage<SysLoginUser> findSysLoginUserPage(SysLoginUserVo sysLoginUserVo) {
        // 设置分页信息
        Page<SysLoginUser> page = new Page<>(sysLoginUserVo.getPageNo(), sysLoginUserVo.getPageSize());
        // 设置查询条件
        LambdaQueryWrapper<SysLoginUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //lambdaQueryWrapper.select(SysLoginUser.class, column -> !column.getColumn().equals("description"));
        //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(sysLoginUserVo.getStatus() != null, SysLoginUser::getStatus, sysLoginUserVo.getStatus());
        // 多列搜索
        lambdaQueryWrapper.and(Vsserts.isNotEmpty(sysLoginUserVo.getKeyword()), wrapper -> wrapper
                .like(SysLoginUser::getNickname, sysLoginUserVo.getKeyword())
                .or()
                .like(SysLoginUser::getPhone, sysLoginUserVo.getKeyword())
                .or()
                .like(SysLoginUser::getNickname, sysLoginUserVo.getKeyword())
        );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(SysLoginUser::getCreateTime);
        // 查询分页返回
        IPage<SysLoginUser> results = this.page(page, lambdaQueryWrapper);
        return results;
    }

    /**
     * 查询轮播图列表信息
     */
    @Override
    public List<SysLoginUser> findSysLoginUserList() {
        // 1：设置查询条件
        LambdaQueryWrapper<SysLoginUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(SysLoginUser::getStatus, 1);
        lambdaQueryWrapper.eq(SysLoginUser::getIsdelete, 0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

    /**
     * 根据id查询轮播图明细信息
     */
    @Override
    public SysLoginUser getSysLoginUserById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改轮播图
     */
    @Override
    public SysLoginUser saveupdateSysLoginUser(SysLoginUser sysLoginUser) {
        boolean flag = this.saveOrUpdate(sysLoginUser);
        return flag ? sysLoginUser : null;
    }


    /**
     * 根据id删除轮播图
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
            List<SysLoginUser> sysLoginUserList = Arrays.stream(strings).map(idstr -> {
                SysLoginUser sysLoginUser = new SysLoginUser();
                sysLoginUser.setId(new Long(idstr));
                sysLoginUser.setIsdelete(1);
                return sysLoginUser;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(sysLoginUserList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}
