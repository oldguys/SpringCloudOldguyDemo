package com.example.oldguy.services;

import com.example.oldguy.modules.auth.services.JwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: JwtServiceTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/29 0029 下午 12:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtServiceTests {

    @Autowired
    private JwtService jwtService;

    @Test
    public void testRefreshTokenToRedis(){
        jwtService.refreshPrivateKeyToRedis();
        jwtService.getPrivateKeyExpire();
    }

    @Test
    public void testGetExpire(){
        jwtService.getPrivateKeyExpire();
    }
}
