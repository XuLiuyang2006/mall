package com.example.mall.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class ExceptionLogAspect {
    //追踪service层的异常，重点跟踪异常来源、定位异常栈。
    @AfterThrowing(pointcut = "execution(* com.example.mall.service.impl..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        // 方法签名
        String method = joinPoint.getSignature().toShortString();

        //参数
        Object[] args = joinPoint.getArgs();

        log.error("❌ 方法 {} 抛出异常：{}", method, ex.getMessage(), ex);
        log.error("❌ 参数列表：{}", Arrays.toString(args));
    }
}
