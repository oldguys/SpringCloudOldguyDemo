package com.example.oldguy.modules.auth.services;

import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.modules.auth.dao.jpas.UserEntityMapper;
import com.example.oldguy.modules.auth.dto.UserRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * @ClassName: UserService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/30 0030 下午 6:57
 **/
@Service
public class UserService {


    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


    public UserRsp getUserRsp(String userId, String password) {

        UserEntity entity = userEntityMapper.findByUserId(userId);

        if (null == entity) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        boolean check = passwordEncoder.matches(entity.getPassword(), passwordEncoder.encode(password));

        if (!check) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }

        entity.setPassword(null);
        UserRsp rsp = new UserRsp();
        rsp.setUser(entity);
        rsp.setToken(jwtService.createTokenFromUser(entity));

        return rsp;
    }

}
