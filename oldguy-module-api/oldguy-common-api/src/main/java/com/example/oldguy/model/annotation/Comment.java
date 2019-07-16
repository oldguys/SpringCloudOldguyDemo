package com.example.oldguy.model.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: Comment
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/5 0005 下午 2:06
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {

    /**
     *  注释
     * @return
     */
    String value();
}
