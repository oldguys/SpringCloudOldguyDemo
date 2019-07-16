package com.example.oldguy.configs;

import com.example.oldguy.interceptors.FeignInterceptor;
import com.example.oldguy.interceptors.TokenInterceptor;
import com.example.oldguy.utils.Log4jUtils;
import com.example.oldguy.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @ClassName: AutoAuthClientConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:06
 **/

@ComponentScan("com.example.oldguy.services")
public class AutoAuthClientConfiguration {

    private String configPath = "classpath:auth-client.properties";

    public static String JWT_INFO_FLAG = "oldguy.jwt-info-header";

    public static String JWT_INFO_NAME = "jwt-info";

    @PostConstruct
    public void init() {

        Log4jUtils.getInstance(AutoAuthClientConfiguration.class).debug("启动 auth-client");

        Properties properties = PropertiesUtils.getProperties(configPath);
        Object value = properties.getProperty(JWT_INFO_FLAG);
        if (null != value) {
            String jwtName = String.valueOf(value);
            if (StringUtils.isNotBlank(jwtName)) {
                JWT_INFO_NAME = String.valueOf(value);
            }
        }
    }

    @Bean
    public TokenInterceptor tokenInterceptor() {
        Log4jUtils.getInstance(AutoAuthClientConfiguration.class).debug("开启 token 解析");
        return new TokenInterceptor();
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        Log4jUtils.getInstance(AutoAuthClientConfiguration.class).debug("开启 feign token 转发");
        return new FeignInterceptor();
    }

    @Bean
    public InterceptorConfiguration interceptorConfig() {
        return new InterceptorConfiguration();
    }

}
