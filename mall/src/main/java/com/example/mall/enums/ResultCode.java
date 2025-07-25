package com.example.mall.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    // 通用成功 & 错误码
    SUCCESS(200, "success"),
    ERROR(500, "服务器内部错误"),
    NOT_FOUND(404, "资源不存在"),
    UNAUTHORIZED(401, "未授权"),
    TOKEN_INVALID(401, "缺少或无效的Token"),

    // 商品相关错误
    PRODUCT_NOT_FOUND(1001, "商品不存在"),
    PRODUCT_EMPTY(1002, "暂无商品数据"),
    PRODUCT_SAVE_FAIL(1003, "商品信息为空，保存失败"),
    PRODUCT_DELETE_FAIL(1004, "商品不存在，无法删除"),

    // 用户相关错误
    USER_NOT_FOUND(1005, "用户不存在"),
    USERNAME_ALREADY_EXISTS(1006, "用户名已存在"),
    LOGIN_ERROR(1007, "用户名或密码错误"),
    USER_UNAUTHORIZED(1008,"用户权限不足"),


    // 购物车相关错误
    CART_ITEM_NOT_FOUND(1101, "购物车中无此商品"),
    CART_ADD_FAILED(1102, "添加到购物车失败"),
    CART_UPDATE_FAILED(1103, "购物车更新失败"),
    CART_REMOVE_FAILED(1104, "购物车删除失败"),

    //订单相关错误
    ORDER_CANNOT_CANCEL(4005, "订单不可取消"),
    ORDER_CANNOT_PAY(4006, "订单不可支付"),
    ORDER_NOT_FOUND(4007,"订单不存在"),

    //地址相关错误
    ADDRESS_NOT_FOUND(4008, "地址不存在"),
    ADDRESS_UNAUTHORIZED(4009, "地址操作未授权"),
    DEFAULT_ADDRESS_NOT_FOUND(4010, "默认地址不存在"),

    //订单支付相关内容
    ORDER_ALREADY_PAID(4011, "订单已支付"),




    // 参数与数据校验相关
    PARAM_ERROR(1200, "参数错误"),
    STOCK_NOT_ENOUGH(1201, "库存不足"),

    // 通用服务器错误
    SERVER_ERROR(500, "服务器异常，请稍后重试");

    public final int code;
    public final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
