package com.example.mall.utils;

import com.example.mall.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    // 成功无返回值
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.code);
        result.setMsg(ResultCode.SUCCESS.message);
        result.setData(null);
        return result;
    }

    // 成功带返回值
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.code);
        result.setMsg(ResultCode.SUCCESS.message);
        result.setData(data);
        return result;
    }

    // 自定义错误消息
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.ERROR.code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    // 枚举错误码
    public static <T> Result<T> error(ResultCode codeEnum) {
        Result<T> result = new Result<>();
        result.setCode(codeEnum.code);
        result.setMsg(codeEnum.message);
        result.setData(null);
        return result;
    }

    // 自定义错误码和消息
    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
