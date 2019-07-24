package com.example.oldguy.modules.auth.services;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.model.workflow.dao.entities.UserEntity;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.utils.Log4jUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: JwtService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/29 0029 上午 11:01
 **/
@Service
public class JwtService {

    @Value("${jwt.key-name}")
    private String privateKeyName;

    @Value("${jwt.algorithm}")
    private String algorithm;

    @Value("${jwt.expire}")
    private Long expireTime;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public String createTokenFromUser(UserEntity user) {

        String privateKey = stringRedisTemplate.opsForValue().get(privateKeyName);

        if (StringUtils.isBlank(privateKey)) {
            throw new RuntimeException("没有找到私钥");
        }

        JwtInfo info = new JwtInfo();
        info.setUserId(user.getUserId());
        info.setUsername(user.getUsername());
        info.setExpireTime(System.currentTimeMillis() + expireTime * 1000);

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(info), new MacSigner(privateKey));

        return jwt.getEncoded();
    }

    public void getPrivateKeyExpire() {
        // 默认单位:秒
        Long timestamp = stringRedisTemplate.opsForValue().getOperations().getExpire(privateKeyName);

        System.out.println("current:" + System.currentTimeMillis());
        System.out.println("timestamp:" + timestamp);
    }

    public void refreshPrivateKeyToRedis() {

        Log4jUtils.getInstance(getClass()).debug("秘钥算法:" + algorithm);
        Log4jUtils.getInstance(getClass()).debug("秘钥:" + privateKeyName);

        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] key = keyGenerator.generateKey().getEncoded();

        String privateKey = Hex.toHexString(key);
        stringRedisTemplate.opsForValue().set(privateKeyName, privateKey);
//        stringRedisTemplate.opsForValue().set(privateKeyName, privateKey, expireTime, TimeUnit.SECONDS);
//        stringRedisTemplate.opsForValue().getOperations().expireAt(privateKeyName, new Date(System.currentTimeMillis() + 60 * 1000));
//        stringRedisTemplate.opsForValue().set(privateKeyName, privateKey, Duration.ofHours(1));
    }
}
