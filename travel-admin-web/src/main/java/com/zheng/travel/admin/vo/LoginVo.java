package com.zheng.travel.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo implements java.io.Serializable {
    // 登录的用户名
    private String username;
    // 登录的密码
    private String password;
    // 登录验证码
    private String code;
    // 验证码缓存的key
    private String uuid;
}
