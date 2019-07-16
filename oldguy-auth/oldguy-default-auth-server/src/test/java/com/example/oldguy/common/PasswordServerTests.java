package com.example.oldguy.common;

import com.example.oldguy.modules.commons.services.PasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: PasswordServerTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 上午 9:50
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordServerTests {

    @Autowired
    private PasswordService passwordService;

    @Test
    public void testGenerateKey() {
        String key = passwordService.generateKey();

        System.out.println();
        System.out.println("key:" + key);
        System.out.println();
    }

}
