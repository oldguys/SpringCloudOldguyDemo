package com.example.oldguy.configs;

import com.example.oldguy.aop.PermsAspect;
import com.example.oldguy.exceptions.PermException;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.utils.Log4jUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: AutoPermAspectConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 8:59
 **/
@Import(value = {AutoAuthClientConfiguration.class})

public class AutoPermAspectConfiguration {

    @Bean
    public PermsAspect permsAspect() {
        Log4jUtils.getInstance(AutoPermAspectConfiguration.class).debug("开启 权限拦截");
        return new PermsAspect();
    }
}
