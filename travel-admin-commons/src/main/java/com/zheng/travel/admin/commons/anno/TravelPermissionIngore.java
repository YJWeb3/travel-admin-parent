package com.zheng.travel.admin.commons.anno;

import java.lang.annotation.*;

/**
 * 权限忽略
 *
 * @author ruoyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TravelPermissionIngore {
    String value() default "";
}