package com.example.oldguy.modules.commons.services;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * @ClassName: PasswordService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/7 0007 下午 7:09
 **/
@Service
public class PasswordService {

    public static final String algorithm = "HMacSHA256";

    private KeyGenerator keyGenerator;
    private Mac mac;

    public PasswordService() throws NoSuchAlgorithmException {
        keyGenerator = KeyGenerator.getInstance(algorithm);
        mac = Mac.getInstance(algorithm);
    }

    /**
     * 创建Key
     *
     * @return
     */
    public String generateKey() {
        SecretKey secretKey = keyGenerator.generateKey();
        return Hex.encodeHexString(secretKey.getEncoded());
    }

    /**
     * @param source
     * @param key
     * @return
     * @throws InvalidKeyException
     * @throws DecoderException
     */
    public String encrypt(String source, String key) throws InvalidKeyException, DecoderException {

        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(key)) {
            throw new RuntimeException("参数不完整！");
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(Hex.decodeHex(key), algorithm);
        mac.init(secretKeySpec);

        return Hex.encodeHexString(mac.doFinal(source.getBytes()));
    }

    public boolean match(String source, String salt, String password) throws DecoderException, InvalidKeyException {
        String temp = encrypt(password, salt);
        return temp.equals(source);
    }

}
