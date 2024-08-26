package com.zheng.travel.admin.config.interceptor.permission;

import com.zheng.travel.admin.commons.anno.TravelPermissionIngore;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.threadlocal.UserThrealLocal;
import com.zheng.travel.admin.commons.utils.collection.CollectionUtils;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.commons.utils.pwd.DesUtils;
import com.zheng.travel.admin.pojo.Permission;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.redis.config.TravelRedisCacheTemplate;
import com.zheng.travel.admin.service.sysloginuser.ISysLoginUserService;
import com.zheng.travel.admin.utils.jwt.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private ISysLoginUserService sysLoginUserService;
    @Autowired
    private TravelRedisCacheTemplate travelRedisCacheTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //4：开始获取用户对应的从redis缓存中获取权限信息
        SysLoginUser sysLoginUser = UserThrealLocal.get();
        if (Vsserts.isNull(sysLoginUser)) {
            throw new TravelValidationException(ResultStatusEnumA.TOKEN_UN_VALID);
        }

        // 1: 获取用户的请求地址.
        String uri = cleanPath(request.getRequestURI());
        // 2: 获取每个请求头的token信息，
        String token = JwtTokenUtils.getJwtToken(request);
        // 判断空的原因是：判断用户可能没有登录，直接访问的地址
        if (Vsserts.isNull(token)) {
            throw new TravelValidationException(ResultStatusEnumA.TOKEN_EMPTY);
        }
        // 3: 增加了一个注解@TravelPermissionIngore ,如果被@TravelPermissionIngore修饰的方法就直接跳过和忽略，代表不进行权限校验
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            TravelPermissionIngore methodAnnotation = handlerMethod.getMethodAnnotation(TravelPermissionIngore.class);
            if (methodAnnotation != null) {
                return true;
            }

            TravelPermissionIngore annotation = handlerMethod.getBean().getClass().getAnnotation(TravelPermissionIngore.class);
            if (annotation != null) {
                return true;
            }
        }

        List<Permission> permissionList = travelRedisCacheTemplate.getCacheObject("sys:user:permission:list:" + token);
        if (CollectionUtils.isEmpty(permissionList)) {
            // 5: 后备处理，如果缓存是null.那么就去数据库查询一次
            permissionList = sysLoginUserService.findBySysPermissionUserId(sysLoginUser.getId());
        }

        log.info("缓存获取权限成功：{}", permissionList);
        //6： 获取用户请求的方法
        // 根据用户请求的地址和权限列表中的地址，进行比较。如果存在说明有权限，
        Long count = permissionList.stream().filter(p -> {
            String url = DesUtils.decrypt(p.getUrl());
            return uri.indexOf(url) != -1;
        }).count();
        log.info("获取权限：{}", count);
        if (count == 0) {
            // 如果不存在就返回0说明，抛出异常
            throw new TravelValidationException(ResultStatusEnumA.NO_PERMISSION);
        }

        // 7: 如果有权限就直接进入到具体方法去执行和处理业务
        return true;
    }


    private static String cleanPath(String path) {
        if (path.indexOf("get/") != -1 || path.indexOf("delete/") != -1) {
            path = path.substring(0, path.lastIndexOf("/"));
        }
        return path;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
