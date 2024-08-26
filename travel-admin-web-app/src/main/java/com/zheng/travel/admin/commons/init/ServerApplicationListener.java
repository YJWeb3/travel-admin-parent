package com.zheng.travel.admin.commons.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ServerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //root application context 没有parent
        if (event.getApplicationContext().getParent() == null) {
            // 逻辑代码
            System.out.println("ApplicationListener=========>");
        }

    }
}