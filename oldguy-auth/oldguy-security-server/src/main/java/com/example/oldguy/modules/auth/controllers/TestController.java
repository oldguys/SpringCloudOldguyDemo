package com.example.oldguy.modules.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: TestController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/26 0026 下午 8:31
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/test")
    public Object test(){
        return "test";
    }
}
