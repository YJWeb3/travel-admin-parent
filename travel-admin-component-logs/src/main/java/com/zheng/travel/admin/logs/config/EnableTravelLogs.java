package com.zheng.travel.admin.logs.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({TravelLogsConfiguration.class})
public @interface EnableTravelLogs {
}
