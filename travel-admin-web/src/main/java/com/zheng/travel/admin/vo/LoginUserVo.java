package com.zheng.travel.admin.vo;

import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo implements java.io.Serializable {
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String token;
    // 存放用户对应的角色
    private List<Role> roleList;
    // 存放用户对应的权限
    private List<Permission> permissionList;
}
