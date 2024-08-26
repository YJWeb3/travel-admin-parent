package com.zheng.travel.admin.vo;

import lombok.Data;

@Data
public class GrantRoleUserVo implements java.io.Serializable {

    // 给用户授权角色
    private Long userId;
    private Long[] roleIds;

    // 给角色授权用
    private Long roleId;
    private Long[] permissionIds;
}
