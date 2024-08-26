package com.zheng.travel.admin.commons.anno;

import com.zheng.travel.admin.commons.enums.LimiterType;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TravelRateLimiter {
    /**
     * 限流key
     */
    String key() default "rate_limiter:";

    /**
     * 限流类型
     */
    LimiterType limitType() default LimiterType.IP;

    /**
     * 限流次数
     * 每timeout限制请求的个数
     */
    int limit() default 10;

    /**
     * 限流时间,单位秒
     */
    int timeout() default 1;
}
