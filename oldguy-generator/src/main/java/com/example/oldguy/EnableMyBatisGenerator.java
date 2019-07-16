package com.example.oldguy;

import com.example.oldguy.configs.MyBatisGeneratorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName: EnableMyBatisGenerator
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/16 0016 下午 9:12
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        MyBatisGeneratorConfiguration.class,
})
public @interface EnableMyBatisGenerator {
}
