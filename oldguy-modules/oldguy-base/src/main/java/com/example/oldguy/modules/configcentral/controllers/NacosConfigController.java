package com.example.oldguy.modules.configcentral.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: NacosConfigController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 下午 5:27
 **/
@RequestMapping("nacos")
@RestController
@RefreshScope
public class NacosConfigController {

    @Value(value = "${test.a:a}")
    public String a;
    @Value(value = "${test.b:b}")
    public String b;
    @Value(value = "${test.c:c}")
    public String c;
    @Value(value = "${test.e:e}")
    public String e;
    @Value(value = "${test.prop1:pro1}")
    public String prop1;
    @Value(value = "${test.prop2:测试}")
    public String prop2;

    @GetMapping("prop1")
    public String getProp1() {

        System.out.println("测试................");

        System.out.println();
        System.out.println("a:" + a);
        System.out.println("b:" + b);
        System.out.println("c:" + c);
        System.out.println("e:" + e);
        System.out.println("prop1:" + prop1);
        System.out.println("prop2:" + prop2);
        System.out.println();

        return prop1 + ":" + prop2;
    }
}
