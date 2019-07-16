package com.example.oldguy.modules.auth.controllers;

import com.example.oldguy.modules.auth.dto.UserRsp;
import com.example.oldguy.modules.auth.services.UserService;
import com.example.oldguy.exceptions.FormValidException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: AuthController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/28 0028 上午 9:11
 **/
@RestController
@RequestMapping("auth")
public class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping("user/login")
    public UserRsp login(String userId, String password) {

        if(StringUtils.isBlank(userId)||StringUtils.isBlank(password)){
            throw new FormValidException("用户名密码不能为空!");
        }

        return userService.getUserRsp(userId,password);
    }
}
