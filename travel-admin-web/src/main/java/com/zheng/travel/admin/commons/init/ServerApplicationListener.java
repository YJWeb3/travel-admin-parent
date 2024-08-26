package com.zheng.travel.admin.commons.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2023-11-23 16:19
 */
@Component
public class ServerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // 逻辑代码
            System.out.println("ApplicationListener=========>");
        }

    }
}