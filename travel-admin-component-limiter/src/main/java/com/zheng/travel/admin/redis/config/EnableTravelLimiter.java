package com.zheng.travel.admin.redis.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({TravelRedisConfiguration.class})
public @interface EnableTravelLimiter {
}
