package com.example.mall.aop;
import com.example.mall.dto.UserDTO;
import com.example.mall.exception.BizException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.example.mall.enums.ResultCode;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminCheckAspect {

    private final HttpSession session; // 你之前登录时用户信息保存在 session 里了

    @Around("@annotation(com.example.mall.annotation.AdminOnly)")
    public Object checkAdmin(ProceedingJoinPoint pjp) throws Throwable {
        Object userObj = session.getAttribute("currentUser");
        System.out.println("userObj.isAdmin() = " );

        if (userObj instanceof UserDTO user) {
            if (Boolean.TRUE.equals(user.isAdmin())) {
                return pjp.proceed(); // 是管理员，放行
            }
        }

        // 非管理员，抛出自定义异常
        throw new BizException(ResultCode.FORBIDDEN, "该操作仅管理员可执行！");
    }
}
