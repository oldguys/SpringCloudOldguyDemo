package com.example.oldguy.modules.auth.services;


import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.modules.auth.dao.jpas.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * 自定义登录授权认证器
 *
 * @author huangrenhao
 * @date 2018/6/7
 */
//@Service
public class SpringAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        System.out.println("Principal:" + username);

        UserEntity entity = userEntityMapper.findByUsername(username);

        if(null == entity){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

