package com.example.mall.annotation;

import java.lang.annotation.*;

//这个注解用于标记需要登录才能访问的方法
/**
 * 登录验证注解
 * 用于标记需要登录才能访问的方法
 * 可以在方法上使用此注解来进行登录验证
 */
//@Target 注解表示这个注解可以用在方法上
//@Retention 注解表示这个注解在运行时仍然可用

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// @interface 注解表示这是一个注解类型，用于定义自定义注解，一般用于标记方法或类，使用时可以在方法上添加这个注解
// @interface 是 Java 中定义注解的关键字
// 这个注解可以用于标记需要登录才能访问的方法
//@interface 的 @是 Java 中定义注解的关键字，表示这是一个注解类型
public @interface LoginRequired {
}
