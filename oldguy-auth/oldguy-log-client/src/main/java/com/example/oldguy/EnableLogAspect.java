package com.example.oldguy;

import com.example.oldguy.configs.AutoLogClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName: EnableLogAspect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:13
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoLogClientConfiguration.class)
public @interface EnableLogAspect {
}
