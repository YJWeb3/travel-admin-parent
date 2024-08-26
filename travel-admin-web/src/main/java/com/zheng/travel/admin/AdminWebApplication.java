package com.zheng.travel.admin;

import com.zheng.orm.travel.anno.EnableMpMybatis;
import com.zheng.travel.admin.redis.config.EnableTravelLimiter;
import com.zheng.travel.admin.resultex.anno.EnableResultEx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTravelLimiter
@EnableMpMybatis
@EnableResultEx
public class AdminWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminWebApplication.class, args);
    }
}
