package com.zheng.travel.admin.resultex.anno;

import com.zheng.travel.admin.resultex.handler.GlobalExceptionControllerHandler;
import com.zheng.travel.admin.resultex.handler.ResultResponseHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({GlobalExceptionControllerHandler.class, ResultResponseHandler.class})
public @interface EnableResultEx {


}
