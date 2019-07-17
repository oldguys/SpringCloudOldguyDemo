package com.example.oldguy.configs;

import com.example.oldguy.aop.LogAspect;
import com.example.oldguy.utils.Log4jUtils;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: AutoLogClientConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:13
 **/
public class AutoLogClientConfiguration {

    @Bean
    public LogAspect logAspect() {
        Log4jUtils.getInstance(AutoLogClientConfiguration.class).debug("开启 日志记录");
        return new LogAspect();
    }
}
