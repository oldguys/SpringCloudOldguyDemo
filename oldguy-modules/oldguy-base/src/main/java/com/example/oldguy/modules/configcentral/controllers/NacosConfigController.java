package com.example.oldguy.modules.configcentral.controllers;

import com.alibaba.nacos.api.config.annotation.NacosValue;
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
//@RefreshScope
public class NacosConfigController {

//    @Value(value = "${test.prop1}")
//    @NacosValue(value = "#{test.prop1}", autoRefreshed = false)
    public String prop1;

    @GetMapping("prop1")
    public String getProp1() {

        System.out.println("测试................");

        return prop1;
    }
}
