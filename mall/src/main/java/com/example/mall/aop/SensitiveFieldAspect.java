package com.example.mall.aop;

import com.example.mall.annotation.SensitiveField;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class SensitiveFieldAspect {

    @Pointcut("execution(* com.example.mall.controller..*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void handleSensitiveParams(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : paramAnnotations[i]) {
                if (annotation instanceof SensitiveField) {
                    SensitiveField sf = (SensitiveField) annotation;
                    Object original = args[i];
                    Object desensitized = desensitizeFields(original, sf.value());
                    log.info("🌐 入参[已脱敏]：{}", desensitized);
                }
            }
        }
    }

    private Object desensitizeFields(Object original, String[] fields) {
        try {
            Object clone = original.getClass().getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(original, clone);

            for (String field : fields) {
                Field f = clone.getClass().getDeclaredField(field);
                f.setAccessible(true);
                f.set(clone, "***");
            }
            return clone;
        } catch (Exception e) {
            log.warn("脱敏失败：{}", e.getMessage());
            return original;
        }
    }
}
