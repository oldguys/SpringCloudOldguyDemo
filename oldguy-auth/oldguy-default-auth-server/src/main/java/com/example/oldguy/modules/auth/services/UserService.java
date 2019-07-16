package com.example.oldguy.modules.auth.services;

import com.example.oldguy.modules.auth.dto.UserRsp;
import com.example.oldguy.services.UserSessionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/4 0004 下午 5:57
 **/
@Service
public class UserService {


    @Autowired
    private JwtService jwtService;

    public UserRsp getUserRsp(String userId, String password) {

        SecurityUtils.getSubject().login(new UsernamePasswordToken(userId, password));

        String token = jwtService.createToken(UserSessionUtils.getUserEntity());

        UserRsp rsp = new UserRsp();
        rsp.setUser(UserSessionUtils.getUserEntity());
        rsp.setToken(token);
        return rsp;
    }
}
