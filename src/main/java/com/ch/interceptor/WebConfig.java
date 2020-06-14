package com.ch.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//配置类，url后台过滤设置
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
//    指的是拦截的url地址具体指哪些，/admin/**指代admin后面的所有地址
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")//排除一些需要访问的admin之后url地址
                .excludePathPatterns("/admin/login");
    }
}
