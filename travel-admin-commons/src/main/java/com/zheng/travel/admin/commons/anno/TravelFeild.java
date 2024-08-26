package com.zheng.travel.admin.commons.anno;

import java.lang.annotation.*;

@Target({ ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TravelFeild {
    String value() default "";
}
