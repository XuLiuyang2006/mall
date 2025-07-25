package com.example.mall.interceptor;

import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器，检查 Session 中是否有 userId。
 * 如果没有，抛出未登录异常，拦截请求。
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false); // false 表示不创建新Session
        if (session == null || session.getAttribute("userId") == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return true; // 已登录，放行
    }
}
