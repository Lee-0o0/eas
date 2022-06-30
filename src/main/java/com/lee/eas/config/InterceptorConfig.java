package com.lee.eas.config;

import com.lee.eas.controller.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")     // 拦截所有路径，包括静态资源路径
                .excludePathPatterns("/js/**","/img/**","/css/**") // 移除某些路径的拦截
                .excludePathPatterns("/admin/login","/","/updatepassword","/student/grade");
    }
}
