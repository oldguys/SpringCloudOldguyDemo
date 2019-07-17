package com.example.oldguy.configs;

import com.example.oldguy.interceptors.TokenInterceptor;
import com.example.oldguy.utils.Log4jUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: InterceptorConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 3:23
 **/
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        Log4jUtils.getInstance(getClass()).debug("启动 TokenInterceptor");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-*/**"
                        , "/v2/api-docs"
                        , "/swagger-resources/configuration/security"
                        , "/swagger-resources/**"
                        , "/webjars/**"
                        , "/error");
    }
}
