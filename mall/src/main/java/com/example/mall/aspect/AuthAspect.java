package com.example.mall.aspect;

import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 登录验证切面
 * 该切面用于在调用带有 @LoginRequired 注解的方法之前检查用户是否已登录。
 * 如果未登录，则抛出 BizException 异常。
 */
@Aspect// AspectJ 注解，表示这是一个切面类
@Component// Spring 注解，表示这是一个 Spring 组件,自动注册到 Spring 容器中
@RequiredArgsConstructor// 使用 Lombok 的 @RequiredArgsConstructor 注解来自动生成构造函数
public class AuthAspect {// 切面类，用于处理登录验证

    private final HttpSession session;// 注入 HttpSession，用于获取当前用户的登录状态

    // 在调用带有 @LoginRequired 注解的方法之前执行
    // @Before 注解表示在切点方法执行之前执行这个方法
    // 切点表达式，匹配所有带有 @LoginRequired 注解的方法
    //@annotation是 AspectJ 的一个切点表达式，用于匹配带有特定注解的方法
    //这里的 @annotation(com.example.mall.annotation.LoginRequired) 表示匹配所有带有 @LoginRequired 注解的方法
    //整个切面类的作用是，在调用带有 @LoginRequired 注解的方法之前，检查用户是否已登录
    //这句@Before 注解表示在调用带有 @LoginRequired 注解的方法之前执行 checkLogin() 方法
    //annotation在Java中是一个关键字，用于定义注解
    //annotation和@的区别在于，annotation是Java中的一个关键字，用于定义注解，而@是用于标记注解的符号
    //切点表达式的关键字是 `@annotation`，它用于匹配带有特定注解的方法。
    //切点表达式的作用是匹配所有带有 @LoginRequired 注解的方法，并在调用这些方法之前执行 checkLogin() 方法。
    @Before("@annotation(com.example.mall.annotation.LoginRequired)")
    public void checkLogin() {
        //checkLogin(): 拦截后执行的方法，如果未登录就抛出异常
        if (session.getAttribute("userId") == null) {
            throw new BizException(ResultCode.UNAUTHORIZED, "请先登录");
        }
    }
}
