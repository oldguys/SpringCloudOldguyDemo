package com.example.oldguy;

import com.example.oldguy.configs.AutoPermAspectConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName: EnablePermAspect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 8:59
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoPermAspectConfiguration.class)
public @interface EnablePermAspect {
}
