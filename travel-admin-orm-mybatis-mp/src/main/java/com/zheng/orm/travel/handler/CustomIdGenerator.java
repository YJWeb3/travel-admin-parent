package com.zheng.orm.travel.handler;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import java.util.Random;

public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Long nextId(Object entity) {
      	//可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
      	String bizKey = entity.getClass().getName();
        //根据bizKey调用分布式ID生成
        long id = new Random().nextLong();
      	//返回生成的id值即可.
        return id;
    }
}