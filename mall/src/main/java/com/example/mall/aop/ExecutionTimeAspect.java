package com.example.mall.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ExecutionTimeAspect {

    // 只拦截 controller 包下所有类的所有方法（你也可以拓展）
    @Pointcut("execution(* com.example.mall.controller..*(..))")
    public void controllerMethods() {}


    // 这个方法会在匹配到的切点方法执行前后被调用
    // joinPoint 参数可以获取到被拦截的方法的信息
    // 例如方法名、参数等
    // 你可以在这个方法中添加自定义逻辑，比如记录方法执行时间、参数等
    // 这里的 @Around 注解表示环绕通知，表示在切点方法执行前后都可以执行自定义逻辑
    //@Around注解在java中是 AspectJ 的一个注解，用于定义环绕通知
    // 环绕通知可以在方法执行前后添加自定义逻辑，比如记录方法执行时间、参数等
    //环绕通知可以在方法中在业务逻辑执行前后添加自定义逻辑，比如记录方法执行时间、参数等
    //业务逻辑的放行可以通过调用 joinPoint.proceed() 方法来实现
    // 这里的 joinPoint.proceed() 方法会执行被拦截的方法，并返回结果，返回的结果可以在方法中使用
    //执行了proceed() 方法后返回的结果是被拦截方法的返回值，你可以在方法中使用这个返回值进行后续处理
    //这里return的结果给了调用者，调用者可以获取到被拦截方法的返回值，而原来的方法也会正常执行，结果不会被改变，会返回给调用者。
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开始计时
        long start = System.currentTimeMillis();

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 结束计时
        long end = System.currentTimeMillis();

        // 计算耗时
        long executionTime = end - start;

        // 打印耗时信息（你也可以选择写到日志、数据库等）
        log.info("方法 {} 执行耗时：{} ms", joinPoint.getSignature(), executionTime);

        return result;
    }
}
