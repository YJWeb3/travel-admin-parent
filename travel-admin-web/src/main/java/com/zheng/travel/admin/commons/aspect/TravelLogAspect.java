package com.zheng.travel.admin.commons.aspect;

import com.zheng.travel.admin.commons.anno.TravelLog;
import com.zheng.travel.admin.commons.ex.TravelBussinessException;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.threadlocal.UserThrealLocal;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.commons.utils.ip.AddressUtils;
import com.zheng.travel.admin.commons.utils.ip.IpUtils;
import com.zheng.travel.admin.commons.utils.ip.UserAgentUtil;
import com.zheng.travel.admin.commons.utils.ip.Visit;
import com.zheng.travel.admin.commons.utils.json.JsonUtil;
import com.zheng.travel.admin.pojo.AdminLogs;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.service.adminlogs.IAdminLogsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect // 代表是一个aop切面
@Slf4j
@Order(2)
@Component
public class TravelLogAspect {

    @Autowired
    private IAdminLogsService adminLogsService;

    // 1: 定义切点，切点标注有注解@KsdLog的的所有函数，通过 @Pointcut 判断才可以进入到具体增强的通知
    @Pointcut("@annotation(com.zheng.travel.admin.commons.anno.TravelLog)")
    public void logpointcut() {
    }

    // 2: 通知 （切点或者切点表达式） @Before+ method execute + @After
    @Around("logpointcut()")
    public Object doAfterReturning(ProceedingJoinPoint proceedingJoinPoint) {

        // 创建一个后台日志对象
        AdminLogs adminLogs = new AdminLogs();
        // 1:方法执行的开始时间
        long starttime = System.currentTimeMillis();
        // 2:执行真实方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        TravelLog annotation = signature.getMethod().getAnnotation(TravelLog.class);
        adminLogs.setClassname(signature.getMethod().getDeclaringClass().getName());
        adminLogs.setClassmethod(signature.getMethod().getName());
        adminLogs.setIsdelete(0);
        adminLogs.setStatus(1);
        if (null != annotation) {
            adminLogs.setDescription(annotation.title());
        }

        SysLoginUser sysLoginUser = UserThrealLocal.get();
        if (null != sysLoginUser) {
            adminLogs.setUserId(sysLoginUser.getId());
            adminLogs.setUsername(sysLoginUser.getNickname());
        }

        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            Visit userAgent = UserAgentUtil.getUserAgent(request);
            if (Vsserts.isNotNull(userAgent)) {
                adminLogs.setOsversion(userAgent.getPlatformType());
                adminLogs.setBroversion(userAgent.getBrowserType());
            }
            String ipAddr = IpUtils.getIpAddr(request);
            if (Vsserts.isNotEmpty(ipAddr)) {
                Map<String, String> realAddressByIPForMap = AddressUtils.getRealAddressByIPForMap(ipAddr);
                adminLogs.setIp(ipAddr);
                adminLogs.setIpaddress(realAddressByIPForMap.get("province") + realAddressByIPForMap.get("city"));
                adminLogs.setProvince(realAddressByIPForMap.get("province"));
                adminLogs.setCity(realAddressByIPForMap.get("city"));
            }
        } catch (Exception ex) {
            log.error("解析ip出错.....");
        }

        try {
            // 方法参数处理
            // 1：获取方法的参数
            Object[] parameters = proceedingJoinPoint.getArgs();
            if (Vsserts.isNotNull(parameters) && parameters.length > 0) {
                StringBuilder builder = new StringBuilder();
                for (Object parameter : parameters) {
                    String s = JsonUtil.obj2String(parameter);
                    if (Vsserts.isNotEmpty(s)) {
                        builder.append(s).append(",");
                    }
                }
                adminLogs.setMethodparams("[" + builder.toString() + "]");
            }
        } catch (Exception ex) {
            log.error("解析参数出错...");
        }

        log.info("1-----around before logs------>日志进来了");
        Object methodReturnValue = null;
        try {
            // 具体springmvc的方法执行 方法执行在这里
            methodReturnValue = proceedingJoinPoint.proceed();

            if (Vsserts.isNull(sysLoginUser)) {
                sysLoginUser = UserThrealLocal.get();
                if (null != sysLoginUser) {
                    adminLogs.setUserId(sysLoginUser.getId());
                    adminLogs.setUsername(sysLoginUser.getNickname());
                }
            }

            // 3:方法执行的结束时间
            long endtime = System.currentTimeMillis();
            // 4：方法的总耗时
            long total = endtime - starttime;
            adminLogs.setMethodtime(total);
            adminLogsService.saveOrUpdate(adminLogs);
            return methodReturnValue;
        } catch (TravelBussinessException ex) {
            throw ex;
        } catch (TravelValidationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }


}
