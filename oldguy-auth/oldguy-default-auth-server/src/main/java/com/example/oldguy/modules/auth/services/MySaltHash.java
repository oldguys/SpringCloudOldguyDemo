package com.example.oldguy.modules.auth.services;

import lombok.Data;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

/**
 * @ClassName: MySaltHash
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 上午 10:53
 **/
@Data
public class MySaltHash implements ByteSource {

    private byte[] salt;

    private String saltHexStr;

    public MySaltHash(byte[] salt) {
        this.salt = salt;
        this.saltHexStr = Hex.encodeToString(salt);
    }

    public MySaltHash(String saltHex) {
        this.saltHexStr = saltHex;
        salt=Hex.decode(saltHex);
    }

    @Override
    public byte[] getBytes() {
        return salt;
    }

    @Override
    public String toHex() {
        return saltHexStr;
    }

    @Override
    public String toBase64() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
