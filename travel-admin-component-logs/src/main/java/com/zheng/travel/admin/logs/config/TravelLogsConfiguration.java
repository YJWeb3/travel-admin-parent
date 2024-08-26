package com.zheng.travel.admin.logs.config;

import com.zheng.travel.admin.logs.aop.TravelLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TravelLogsConfiguration {

    @Bean
    public TravelLogAspect TravelLogAspect() {
        return new TravelLogAspect();
    }


}