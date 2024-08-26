package com.zheng.travel.admin.config.interceptor.jwt;

import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelBussinessException;
import com.zheng.travel.admin.commons.threadlocal.UserThrealLocal;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.redis.config.TravelRedisCacheTemplate;
import com.zheng.travel.admin.service.jwt.IJwtBlackService;
import com.zheng.travel.admin.service.sysloginuser.ISysLoginUserService;
import com.zheng.travel.admin.utils.jwt.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private ISysLoginUserService sysLoginUserService;
    @Autowired
    private TravelRedisCacheTemplate travelRedisCacheTemplate;
    @Autowired
    @Qualifier("jwtBlackStringService")
    IJwtBlackService jwtBlackService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1: 判断当前请求头中是否有token
        String token = JwtTokenUtils.getJwtToken(request);
        if (Vsserts.isEmpty(token)) {
            throw new TravelBussinessException(ResultStatusEnumA.TOKEN_EMPTY);
        }

        // 2:判断当前token是不是黑名单里
        if (jwtBlackService.isBlackList(token)) {
            throw new TravelBussinessException(ResultStatusEnumA.TOKEN_EMPTY_EXPIRED);
        }

        // 3: 校验token 是否过期 isverfiy = false 代表过期
        boolean isverfiy = JwtTokenUtils.isverfiy(token);
        String tokenKey = "sys:user:token:" + token;
        if (!isverfiy) {
            // 4：如果你过期了就从缓存中去获取token
            token = travelRedisCacheTemplate.getCacheObject(tokenKey);
        }

        // 5：建议使用这种方式,这种具有实时性  5==== status= 1
        String userId = JwtTokenUtils.parseToken(token);
        if (Vsserts.isNull(userId) || Vsserts.isNull(travelRedisCacheTemplate.getCacheObject(tokenKey))) {
            throw new TravelBussinessException(ResultStatusEnumA.TOKEN_UN_VALID);
        }

        SysLoginUser user = sysLoginUserService.getById(Long.parseLong(userId));
        // 6：判断用户是否激活
        if (user.getStatus().equals(0)) {
            throw new TravelBussinessException(ResultStatusEnumA.TOKEN_UN_VALID);
        }

        // 7：判断是否需要续期 30分钟就续期
        if (travelRedisCacheTemplate.getExpireTime(tokenKey) < 60 * 40) {
            String newToken = JwtTokenUtils.createToken(user);
            // 缓存也进行同步一下缓存
            travelRedisCacheTemplate.setCacheObject("sys:user:permission:list:"+token, sysLoginUserService.findBySysPermissionUserId(user.getId()));
            travelRedisCacheTemplate.setCacheObject(tokenKey, newToken, JwtTokenUtils.REDIS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS);
        }

        UserThrealLocal.put(user);

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserThrealLocal.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThrealLocal.remove();
    }
}
