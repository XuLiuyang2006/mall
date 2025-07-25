package com.example.mall.handler;

import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e) {
//        e.printStackTrace(); // 可以写入日志
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleOtherExceptions(Exception e) {
        e.printStackTrace(); // 可以写入日志
        return Result.error(ResultCode.SERVER_ERROR);// 使用枚举类返回错误信息
    }
}