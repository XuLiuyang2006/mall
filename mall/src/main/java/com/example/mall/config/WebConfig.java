package com.example.mall.config;

import com.example.mall.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // 需要登录才能访问的接口路径，支持通配符
                .addPathPatterns("/api/users/me", "/api/users/me/**", "/api/orders/**", "/api/cart/**")

                // 登录和注册接口不拦截
                .excludePathPatterns("/api/users/login", "/api/users/register", "/error","/api/products/**");
    }

    // 如果你还想配置跨域等，也可以写在这里


    @Override
    public void addCorsMappings(CorsRegistry registry) {// 配置跨域请求
        registry.addMapping("/**")// 允许所有路径的跨域请求
                .allowedOriginPatterns("http://localhost:5173") // 你的前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")// 允许的 HTTP 方法
                .allowCredentials(true); // 关键：允许携带 Cookie
    }

}
