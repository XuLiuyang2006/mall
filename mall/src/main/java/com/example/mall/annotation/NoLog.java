package com.example.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标记敏感字段的注解
 * 用于标识在序列化或日志记录时需要进行脱敏处理的字段。
 * 可以在实体类的字段上使用此注解。
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 可以应用于方法或类
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // 在运行时可见
public @interface NoLog {
}
