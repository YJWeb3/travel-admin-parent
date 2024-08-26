package com.zheng.travel.admin.commons.enums;

public enum LimiterType {
    /**
     * 默认策略全局限流
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP;
}
