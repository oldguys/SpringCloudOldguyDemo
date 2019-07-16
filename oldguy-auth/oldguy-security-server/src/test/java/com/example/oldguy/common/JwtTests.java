package com.example.oldguy.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.oldguy.model.dto.JwtInfo;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: JwtTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/29 0029 上午 10:43
 **/
public class JwtTests {


    @Test
    public void test() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("HMACSHA256");

        byte[] key = keyGenerator.generateKey().getEncoded();

        String privateKey = Hex.toHexString(key);

        System.out.println("privateKey:" + privateKey);


        JwtInfo userContext = new JwtInfo();

        userContext.setUserId(IdWorker.getIdStr());
        userContext.setUsername("测试123456");

        String json = JSON.toJSONString(userContext);

        System.out.println("json:" + json);

        Jwt jwt = JwtHelper.encode(json, new MacSigner(privateKey));

        System.out.println(jwt.getEncoded());


    }

}
