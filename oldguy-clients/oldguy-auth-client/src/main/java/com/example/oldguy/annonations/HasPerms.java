package com.example.oldguy.annonations;

import java.lang.annotation.*;

/**
 * @ClassName: HasPerms
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:01
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPerms {

    String[] roles();

    Logical logical() default Logical.AND;

    enum Logical {
        AND, OR
    }
}
