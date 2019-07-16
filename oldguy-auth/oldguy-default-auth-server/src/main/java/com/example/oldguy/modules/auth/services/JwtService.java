package com.example.oldguy.modules.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.example.oldguy.model.constans.JwtInfoConstants;
import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.utils.Log4jUtils;
import org.apache.shiro.codec.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @ClassName: JwtService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 下午 2:14
 **/
@Service
public class JwtService {


    @Value("${jwt.key-name}")
    private String privateKeyName;

    @Value("${jwt.algorithm}")
    private String algorithmName;

    @Value("${jwt.expire}")
    private Long expireTime;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 秘钥生成器
     */
    private KeyGenerator keyGenerator;

    /**
     * 加密算法
     */
    private Algorithm algorithm;

    /**
     * 校验类
     */
    public static JWTVerifier jwtVerifier;

    /**
     * 校验器 用于生成 JWTVerifier 校验器
     */
    public static Verification verification;

    public JwtService() throws NoSuchAlgorithmException {
        keyGenerator = KeyGenerator.getInstance(JwtInfoConstants.MAC_NAME);
    }

    @PostConstruct
    public void init() {

        algorithm = Algorithm.HMAC256(getPrivateKey());
        verification = JWT.require(algorithm);
        jwtVerifier = verification.build();
    }

    /**
     * 刷新私钥
     */
    public void refeshPrivateKey() {

        SecretKey secretKey = keyGenerator.generateKey();

        String privateKey = Hex.encodeToString(secretKey.getEncoded());
        stringRedisTemplate.opsForValue().set(privateKeyName, privateKey);

        init();
    }

    private byte[] getPrivateKey() {

        String privateKey = stringRedisTemplate.opsForValue().get(privateKeyName);

        if (!StringUtils.isEmpty(privateKey)) {
            return Hex.decode(privateKey);
        }

        SecretKey secretKey = keyGenerator.generateKey();

        privateKey = Hex.encodeToString(secretKey.getEncoded());
        stringRedisTemplate.opsForValue().set(privateKeyName, privateKey);

        return secretKey.getEncoded();
    }

    public String createToken(UserEntity userEntity) {

        JWTCreator.Builder builder = JWT.create();

        builder.withExpiresAt(new Date());
        builder.withClaim(JwtInfoConstants.USER_ID, userEntity.getUserId())
                .withClaim(JwtInfoConstants.USERNAME, userEntity.getUsername());

        Date expireTime = new Date(System.currentTimeMillis() + this.expireTime * 1000);

        Log4jUtils.getInstance(getClass()).debug("过期时间：" + expireTime);

        builder.withExpiresAt(expireTime);

        return builder.sign(algorithm);
    }

    public JwtInfo checkToken(String token) {

        DecodedJWT jwt = jwtVerifier.verify(token);

        JwtInfo info = new JwtInfo();
        info.setUserId(jwt.getClaims().get(JwtInfoConstants.USER_ID).asString());
        info.setUsername(jwt.getClaims().get(JwtInfoConstants.USERNAME).asString());
        info.setExpireTime(jwt.getExpiresAt());

        return info;
    }

}
