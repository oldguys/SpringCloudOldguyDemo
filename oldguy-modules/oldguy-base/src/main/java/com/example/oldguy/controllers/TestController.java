package com.example.oldguy.controllers;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.annonations.HasPerms;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.services.UserSessionUtils;
import org.springframework.util.Base64Utils;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: TestController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/11 0011 下午 3:15
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/jwt")
    @HasPerms(roles = {"test","role1"})
    public JwtInfo jwt()  {
        return UserSessionUtils.getJwtInfo();
    }

}
