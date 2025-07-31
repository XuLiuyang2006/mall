package com.example.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 该注解可以应用于方法参数
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {
    String[] value();// 指定需要脱敏的字段名数组
}
