package com.zheng.travel.admin.service.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JwtBlackStringService implements IJwtBlackService {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 添加黑白名单
     *
     * @param token
     */
    @Override
    public void addBlackList(String token) {
        try {
            log.info("添加token黑名单token:{}....", token);
            // 2: 同时黑名单的set中也添加一份
            if (!this.redisTemplate.hasKey(BLACK_STRING_KEY + token)) {
                this.redisTemplate.opsForValue().set(BLACK_STRING_KEY + token, token, BLACK_EXPIRE_TIME, TimeUnit.MINUTES);
            }
        } catch (Exception ex) {
            log.info("redis服务器出故障了....");
        }
    }

    /**
     * 判断当前token是否在黑名单中
     *
     * @param token
     * @return
     */
    @Override
    public boolean isBlackList(String token) {
        log.info("添加用户黑名单....");
        boolean flag = false;
        try {
            flag = this.redisTemplate.hasKey(BLACK_STRING_KEY + token);
        } catch (Exception ex) {
            // 这里出现异常一般指你的redis服务崩溃或者服务器链接超时或者一些不可抗的因素
            log.error("出现异常", ex.getMessage());
            // todo 可以考虑数据库对比一次
        }
        return flag;
    }

    /**
     * 把token从黑名单删除
     *
     * @param token
     * @return
     */
    @Override
    public boolean removeBlackList(String token) {
        log.info("添加用户黑名单....");
        boolean flag = false;
        try {
            // 1：移除redis黑名单
            return this.redisTemplate.delete(BLACK_STRING_KEY + token);
        } catch (Exception ex) {
            log.error("出现异常", ex.getMessage());
        }
        return flag;
    }

}
