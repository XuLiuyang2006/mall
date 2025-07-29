package com.example.mall.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/*
 * 日志切面类，用于记录 UserController 中方法的调用日志
 * 这个类使用了 AspectJ 和 Spring AOP 来实现日志记录功能
 * 在 UserController 中的所有方法执行时，都会触发这个切面，记录方法名、参数和返回值等信息
 */

@Slf4j// 使用 Lombok 的 @Slf4j 注解来简化日志记录
@Aspect// AspectJ 注解，表示这是一个切面类
@Component// Spring 注解，表示这是一个 Spring 组件,自动注册到 Spring 容器中
public class LogAspect {// 切面类，用于记录日志

    // 定义一个切点，匹配 UserController 中的所有方法
    // 切点表达式，匹配 UserController 中的所有方法
    // 这里的 * 表示任意返回类型，.. 表示任意参数列表
    // 可以根据需要修改切点表达式来匹配其他控制器或方法
    // 例如，如果要匹配所有控制器，可以使用 execution(* com.example.mall.controller..*.*(..))
    // 也可以使用 @Pointcut 注解来定义更复杂的切点表达式
    //execution(* com.example.mall.controller.UserController.*(..)) 表示匹配 UserController 中的所有方法
    //esecution表示执行方法的切点表达式
    //* 表示任意返回类型，比如 void、String、int 等，根据实际情况可以修改为具体的返回类型，不同的返回类型可以使用不同的切点表达式
    // 切点方法，表示在 UserController 中的所有方法执行时都会触发这个切点
    @Pointcut("execution(* com.example.mall.controller.UserController.*(..))")
    public void userControllerMethods() {}


    //这个切点表达式是 AspectJ 的语法，表示匹配 UserController 中的所有方法
    //具体代码是pointcut("execution(* com.example.mall.controller.UserController.*(..))")
    //userControllerMethods() 是一个切点方法，表示在 UserController 中的所有方法执行时都会触发这个切点
    //切点方法，定义了一个切点，匹配 UserController 中的所有方法，如果需要匹配其他控制器或方法，可以修改切点表达式
    //作用是为了在切面中使用这个切点，避免重复编写相同的切点表达式，
    //可以在切面中使用 @Before、@AfterReturning、@AfterThrowing 等注解来定义切点的通知方法


    // @Before 注解表示在切点方法执行之前执行这个方法，userControllerMethods() 是切点表达式，里面定义了切点，调用这个方法时会触发切点
    //触发切点时，Spring AOP 会自动调用这个方法，这个方法可以获取切点方法的信息，比如方法名、参数等
    //这个方法一般用于记录日志、处理权限等操作
    //userControllerMethods() 是上面定义的切点方法，表示在 UserController 中的所有方法执行时都会触发这个切点
    //userControllerMethods() 方法是系统自动生成的，表示在 UserController 中的所有方法执行时都会触发这个切点
    //触发这个切点时，Spring AOP 会自动调用下面的三个方法，这些方法可以记录日志、处理异常等操作
    //切点是 AspectJ 中的概念，表示程序执行的某个点，比如方法调用、异常抛出等
    //在这里，切点表示 UserController 中的所有方法执行时都会触发这个切点
    // 切点方法 userControllerMethods() 定义了一个切点，这个切点是匹配 UserController 中的所有方法执行
    // 在切点方法执行之前、执行之后和抛出异常时，都会触发下面的通知方法，是分别在切点方法执行前、执行后和抛出异常时执行的通知方法

    //
    // JoinPoint 参数表示连接点，可以获取方法名、参数等信息
    // @AfterReturning 注解表示在切点方法执行成功后执行这个方法
    // returning 属性表示返回值的名称，可以在方法中使用
    // @AfterThrowing 注解表示在切点方法执行抛出异常时执行这个方法
    // throwing 属性表示异常的名称，可以在方法中使用
    // 下面的三个方法分别对应切点方法执行前、执行后和抛出异常时的通知方法
    // 这些方法可以记录日志、处理异常等
    //
    @Before("userControllerMethods()")
    // 在切点方法执行之前执行这个方法
    public void logBefore(JoinPoint joinPoint) {// 在切点方法执行之前执行这个方法
        // 获取方法名和参数
        // JoinPoint 是 AOP 中的一个接口，表示连接点，可以获取方法名、参数等信息
        // joinPoint.getSignature().toShortString() 获取方法的签名信息
        // joinPoint.getArgs() 获取方法的参数列表
        // 使用 Lombok 的 @Slf4j 注解来简化日志记录
        // 使用 log.info() 方法记录日志，日志级别为 INFO
        // 这里使用了 Lombok 的 @Slf4j 注解来简化日志记录
        // 记录调用方法和参数
        // 这里的 log 是 Lombok 提供的日志对象，可以直接使用 log.info()、log.error() 等方法记录日志
        // Object[] args = joinPoint.getArgs() 获取方法的参数列表
        String method = joinPoint.getSignature().toShortString();
        //捕获类名
        String className = joinPoint.getTarget().getClass().toString();
        Object[] args = joinPoint.getArgs();
        log.info("🟢 调用方法: {}，参数: {},类名:{}", method, Arrays.toString(args), className);
    }

    // @AfterReturning 注解表示在切点方法执行成功后执行这个方法
    // returning 属性表示返回值的名称，可以在方法中使用
    // 这个方法会在切点方法执行成功后执行，可以获取返回值并记录日志
    //pointcut 属性表示切点表达式，表示在 UserController 中的所有方法执行成功后执行这个方法
    // returning 属性表示返回值的名称，返回值是切点方法的返回值，可以在方法中使用
    // userControllerMethods() 是上面定义的切点方法，表示在 UserController 中的所有方法执行成功后执行这个方法

    //logAfter 方法会在切点方法执行成功后执行，可以获取返回值并记录日志
    @AfterReturning(pointcut = "userControllerMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        //joinPoint 参数表示连接点，可以获取方法名、参数等信息
        // result 参数表示切点方法的返回值，可以在方法中使用

        log.info("✅ 返回值: {}", result);
    }

    // @AfterThrowing 注解表示在切点方法执行抛出异常时执行这个方法
    // throwing 属性表示异常的名称，可以在方法中使用
    // 这个方法会在切点方法执行抛出异常时执行，可以获取异常信息并记录日志
    // pointcut 属性表示切点表达式，表示在 UserController 中的所有方法执行抛出异常时执行这个方法
    @AfterThrowing(pointcut = "userControllerMethods()", throwing = "e")
    public void logException(JoinPoint joinPoint, Throwable e) {
        log.error("❌ 异常方法: {}, 错误信息: {}", joinPoint.getSignature(), e.getMessage());
    }


    @Pointcut("execution(* com.example.mall.controller.*.*(..))")
    public void allControllerMethods() {}

    @Before("allControllerMethods()")
    public void logAllControllerMthods(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().toString();
        log.info("🟢 调用方法: {}，参数: {},类名:{}", method, Arrays.toString(args), className);
    }

}
