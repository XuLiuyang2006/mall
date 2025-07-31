package com.example.mall.aop;

import com.example.mall.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OperationLogAspect {
    // 这里可以添加切点和通知方法来记录操作日志
    // 例如，使用 @Before、@After、@Around 等注解来定义切点和通知方法

    // 示例：记录方法执行时间
    // @Around("execution(* com.example.mall.service..*(..))")
    // public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    //     long start = System.currentTimeMillis();
    //     Object proceed = joinPoint.proceed();
    //     long executionTime = System.currentTimeMillis() - start;
    //     log.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
    //     return proceed;
    // }

    @Around("@annotation(operationLog)")
    public Object LogOperation(ProceedingJoinPoint proceedingJoinPoint, OperationLog operationLog) throws Throwable{
        String methodName = proceedingJoinPoint.getSignature().toShortString();
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
