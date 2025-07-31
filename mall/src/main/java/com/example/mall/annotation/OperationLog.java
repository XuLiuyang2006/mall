package com.example.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE}) // 该注解可以应用于方法
@Retention(RetentionPolicy.RUNTIME)// 该注解在运行时仍然可用
public @interface OperationLog {
    String value() default ""; // 操作描述，可以在使用注解时提供具体的操作信息
    // 例如：@OperationLog("添加用户地址")
    // 这将记录添加用户地址的操作日志
    // 也可以不提供默认值，使用时必须指定操作描述
    // 例如：@OperationLog("删除用户地址")
    // 这将记录删除用户地址的操作日志

}
