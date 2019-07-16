package com.example.oldguy.dao;

import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.modules.auth.dao.jpas.UserEntityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: UserEntityMapperTest
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/28 0028 上午 10:59
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityMapperTest {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testSave() {
        UserEntity userEntity = getInstance("张三", "abc123456");
        userEntityMapper.save(userEntity);
    }

    private UserEntity getInstance(String username, String password) {

        UserEntity entity = new UserEntity();

        entity.setUsername(username);
        entity.setPassword(passwordEncoder.encode(password));

        return entity;
    }
}
