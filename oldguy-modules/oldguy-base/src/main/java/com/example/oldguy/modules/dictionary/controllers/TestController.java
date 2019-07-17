package com.example.oldguy.modules.dictionary.controllers;

import com.example.oldguy.annonations.HasPerms;
import com.example.oldguy.annonations.LogControl;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.services.UserSessionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: TestController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/11 0011 下午 3:15
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @LogControl(message = "测试 jwt")
    @GetMapping("/jwt")
    @HasPerms(roles = {"test", "role1"})
    public JwtInfo jwt() {
        return UserSessionUtils.getJwtInfo();
    }

}
