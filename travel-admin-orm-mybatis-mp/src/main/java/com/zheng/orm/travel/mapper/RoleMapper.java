package com.zheng.orm.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zheng.travel.admin.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 查询用户对应的权限
     *
     * @param roleid
     * @return
     */
    List<Long> selectRolePermission(@Param("roleid") Long roleid);

}