package com.example.oldguy.annonations;

import java.lang.annotation.*;

/**
 * @ClassName: LogControl
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:15
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogControl {

    LogType type() default LogType.CRUD;

    String message() default "";

    enum LogType {
        CRUD, PERM;
    }

}
