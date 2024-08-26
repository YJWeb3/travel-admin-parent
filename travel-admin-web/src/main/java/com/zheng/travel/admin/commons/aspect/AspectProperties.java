package com.zheng.travel.admin.commons.aspect;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "travel.aop")
@Data
public class AspectProperties {
    private boolean isFlag = true;
}
