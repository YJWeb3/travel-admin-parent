package com.zheng.travel.admin.logs.aop;

import com.zheng.travel.admin.commons.anno.TravelLog;
import com.zheng.travel.admin.commons.threadlocal.UserThrealLocal;
import com.zheng.travel.admin.commons.utils.ip.IpUtils;
import com.zheng.travel.admin.commons.utils.req.ServletUtils;
import com.zheng.travel.admin.pojo.SysLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;


@Aspect // 代表是一个aop切面
@Slf4j
@Order(1)
public class TravelLogAspect {


    // 2: 通知 （切点或者切点表达式）
    @Around("@annotation(TravelLog)")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint, TravelLog TravelLog) throws Throwable {
        // 1:方法执行的开始时间
        long starttime = System.currentTimeMillis();
        // 2:执行真实方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 3：核心代码，这个前后通知分界线
        Object methodReturnValue = proceedingJoinPoint.proceed();
        log.info("1------>日志进来了=====当前执行方法的返回值是：{}", methodReturnValue);
        // 4:方法执行的结束时间
        long endtime = System.currentTimeMillis();
        // 5：方法的总耗时
        long total = endtime - starttime;
        log.info("2------->日志进来了=====当前方法:{}，执行的时间是：{} ms", signature.getMethod().getName(), total);
        return methodReturnValue;

    }

    // 2: 通知 （切点或者切点表达式）
    @AfterThrowing(value = "@annotation(TravelLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, TravelLog TravelLog, Exception e) {
        // 1:方法执行的开始时间
        long starttime = System.currentTimeMillis();
        // 2:执行真实方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("3--------->日志进来了=====方法：{}，出现异常了:{}", signature.getMethod().getName(), e.getMessage());
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            SysLoginUser sysLoginUser = UserThrealLocal.get();
            // *========数据库日志=========*//
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }
}
