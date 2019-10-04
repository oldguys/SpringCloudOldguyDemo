package com.example.oldguy.configs;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName: EnableXhrReq
 * @Author: ren
 * @Description: 配置跨域
 * @CreateTIme: 2019/9/29 0029 下午 4:49
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AjaxConfiguration.class)
public @interface EnableXhrReq {
}
