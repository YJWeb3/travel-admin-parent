package com.zheng.travel.admin.service.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtBlackSetService implements IJwtBlackService {

    @Autowired
    public RedisTemplate redisTemplate;
    /**
     * 添加黑白名单
     * @param token
     */
    @Override
    public void addBlackList(String token) {
        log.info("添加token黑名单token:{}....", token);
        // 2: 同时黑名单的set中也添加一份
        if (!this.redisTemplate.opsForSet().isMember(BLACK_LIST_KEY, token)) {
            this.redisTemplate.opsForSet().add(BLACK_LIST_KEY, token);
        }
    }

    // 2: 判断当前用户是否在黑名单中
    @Override
    public boolean isBlackList(String token) {
        log.info("添加用户黑名单....");
        boolean flag = false;
        try {
            flag = this.redisTemplate.opsForSet().isMember(BLACK_LIST_KEY, token);
        } catch (Exception ex) {
            // 这里出现异常一般指你的redis服务崩溃或者服务器链接超时或者一些不可抗的因素
            log.error("出现异常", ex.getMessage());
            // todo 可以考虑数据库对比一次
        }
        return flag;
    }

    // 4: 删除黑名单
    @Override
    public boolean removeBlackList(String token) {
        log.info("添加用户黑名单....");
        boolean flag = false;
        try {
            // 1：移除redis黑名单
            Long remove = this.redisTemplate.opsForSet().remove(BLACK_LIST_KEY, token);
            // 2：移除数据库DB黑名单
            // todo
            flag = remove > 0;
        } catch (Exception ex) {
            log.error("出现异常", ex.getMessage());
        }
        return flag;
    }

}
