package com.example.oldguy.modules.auth.controllers;

import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.modules.auth.dto.UserRsp;
import com.example.oldguy.modules.auth.services.JwtService;
import com.example.oldguy.modules.auth.services.UserService;
import com.example.oldguy.exceptions.FormValidException;
import com.example.oldguy.services.AuthClientApi;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class AuthController implements AuthClientApi {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Override
    public RspMsg<JwtInfo> checkToken(String token) {
        JwtInfo info = jwtService.checkToken(token);
        return new RspMsg<JwtInfo>().data(info).defaultSuccessMessage();
    }

    @PostMapping("login")
    public UserRsp login(String userId, String password) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(password)) {
            throw new FormValidException("用户名密码不能为空!");
        }

        return userService.getUserRsp(userId, password);
    }

    @GetMapping("test/normal")
    public String testNormal(){
        return "test没有权限";
    }

    @RequiresRoles("test")
    @GetMapping("test/role")
    public String testRole() {
        return "测试 test 权限";
    }

}
