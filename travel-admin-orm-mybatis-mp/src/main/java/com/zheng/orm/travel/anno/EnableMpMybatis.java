package com.zheng.orm.travel.anno;

import com.zheng.orm.travel.config.MpMyBaticConfigration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MpMyBaticConfigration.class})
public @interface EnableMpMybatis {
}
