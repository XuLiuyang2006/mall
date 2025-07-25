package com.example.mall.exception;

import com.example.mall.enums.ResultCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final int code;

    // 构造函数：支持 ResultCode
    public BizException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    // ✅ 新增：支持 ResultCode + 自定义消息
    public BizException(ResultCode resultCode, String message) {
        super(resultCode.getMessage()+":"+message);
        this.code = resultCode.getCode();
    }


    // ✅ 新增：自定义 code 和 message（非枚举）
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }
}
