package com.example.myblog.config;


import com.example.myblog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    //拦截器注册到容器中&指定拦截规则


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new LoginInterceptor()) //拦截器注册到容器中
               .addPathPatterns("/admin/**") //admin下的所有请求都被拦截
               .excludePathPatterns("/admin","/admin/login"); //放行的请求
    }
}
