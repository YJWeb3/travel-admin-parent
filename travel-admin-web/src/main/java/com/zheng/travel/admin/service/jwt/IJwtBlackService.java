package com.zheng.travel.admin.service.jwt;


public interface IJwtBlackService {

    String BLACK_STRING_KEY = "blacklist:string";
    Long BLACK_EXPIRE_TIME = 30L;
    String BLACK_LIST_KEY = "blacklist:set";

    //添加黑白名单
    void addBlackList(String token);

    // 2: 判断当前用户是否在黑名单中
    boolean isBlackList(String token);

    // 4: 删除黑名单
    boolean removeBlackList(String token);
}
