package com.example.mall.aop;

import com.example.mall.annotation.NoLog;
import com.example.mall.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    @Around("@annotation(operationLog)")
    public Object LogOperation(ProceedingJoinPoint proceedingJoinPoint, OperationLog operationLog) throws Throwable{
        String methodName = proceedingJoinPoint.getSignature().toShortString();

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = proceedingJoinPoint.getTarget().getClass();

        // 检查方法或类上是否有 @NoLog 注解
        if (method.isAnnotationPresent(NoLog.class) || targetClass.isAnnotationPresent(NoLog.class)) {
            return proceedingJoinPoint.proceed(); // 直接执行，不记录日志
        }


        Object[] args = proceedingJoinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        //这里的描述是通过注解传入的，比如 @OperationLog("添加用户地址")，那么描述就是 "添加用户地址"
        //如果没有提供描述，则默认为空字符串
        //也就是说这个描述是通过注解的 value 属性传入的
        //String value() default ""; // 操作描述，可以在使用注解是提供具体的操作信息
        log.info("[操作日志] 方法: {}, 描述: {}, 参数: {}, 耗时: {} ms",
                methodName,
                operationLog.value(),
                args,
                duration);
        return result;
    }

}
