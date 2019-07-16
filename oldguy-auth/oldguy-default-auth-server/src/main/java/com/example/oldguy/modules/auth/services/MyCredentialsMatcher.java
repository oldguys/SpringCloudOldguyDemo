package com.example.oldguy.modules.auth.services;

import com.example.oldguy.modules.commons.services.PasswordService;
import com.example.oldguy.services.UserSessionUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;

/**
 * @ClassName: MyCredentialsMatcher
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/6 0006 下午 9:12
 **/
@Service
public class MyCredentialsMatcher implements CredentialsMatcher {

    @Autowired
    private PasswordService passwordService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        SimpleAuthenticationInfo temp = (SimpleAuthenticationInfo) info;

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String checkSource = new String(usernamePasswordToken.getPassword());
        try {
            token.getCredentials();
            return passwordService.match(temp.getCredentials().toString(), temp.getCredentialsSalt().toHex(), checkSource);
        } catch (DecoderException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        UserSessionUtils.remove();
        return false;
    }
}
