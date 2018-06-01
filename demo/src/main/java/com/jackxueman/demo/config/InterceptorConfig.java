package com.jackxueman.demo.config;


import com.jackxueman.demo.common.interceptor.AuthInterceptor;
import com.jackxueman.demo.common.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Interceptor Config
 */
@EnableWebMvc
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    AuthInterceptor sessionInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    RequestInterceptor requestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor())
                .addPathPatterns("/demo/**");

        registry.addInterceptor(sessionInterceptor())
                .addPathPatterns("/demo/**")
                .excludePathPatterns(
                        "/demo/auth/login",
                        "/demo/auth/logout");
    }
}
