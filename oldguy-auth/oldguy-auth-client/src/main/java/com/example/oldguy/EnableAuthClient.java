package com.example.oldguy;

import com.example.oldguy.configs.AutoAuthClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName: EnableAuthClient
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:00
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoAuthClientConfiguration.class)
public @interface EnableAuthClient {

}
