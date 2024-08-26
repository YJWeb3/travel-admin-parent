package com.zheng.travel.admin.commons.threadlocal;

import com.zheng.travel.admin.pojo.SysLoginUser;

public class UserThrealLocal {

    // 本地线程缓存: ThreadLocal 底层就是 Map
    private static ThreadLocal<SysLoginUser> userThreadLocal = new ThreadLocal<SysLoginUser>();

    public static void put(SysLoginUser user) {
        userThreadLocal.set(user);
    }

    public static SysLoginUser get() {
        return userThreadLocal.get();
    }

    public static void remove() {
        userThreadLocal.remove();
    }

}
