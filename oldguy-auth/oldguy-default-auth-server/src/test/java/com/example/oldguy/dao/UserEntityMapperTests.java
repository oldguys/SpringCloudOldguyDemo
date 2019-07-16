package com.example.oldguy.dao;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.modules.auth.dao.jpas.UserEntityMapper;
import com.example.oldguy.modules.commons.services.PasswordService;
import com.example.oldguy.services.UserSessionUtils;
import org.apache.commons.codec.DecoderException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEntityMapperTests {

    @Autowired
    private UserEntityMapper userEntityMapper;
    @Autowired
    private PasswordService passwordService;

    @Before
    public void before() {
        UserEntity entity = new UserEntity();
        entity.setUserId("10010");
        entity.setUsername("测试用户");
        UserSessionUtils.pushUserEntity(entity);

        System.out.println("初始化用户信息：" + Thread.currentThread().getId());
    }

    @Test
    public void testUpdateBatch(){
        List<UserEntity> list = userEntityMapper.findAll(null);
        userEntityMapper.updateBatch(list);
    }

    @Test
    public void testUpdate(){
        UserEntity entity = userEntityMapper.findOne("1148482064002093064");
        userEntityMapper.update(entity);
    }

    @Test
    public void testSaveBatch() throws DecoderException, InvalidKeyException {

        List<UserEntity> userEntities = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            UserEntity userEntity = getInstance("张三" + i, "abc123456");
            userEntities.add(userEntity);

        }

        userEntityMapper.saveBatch(userEntities);
    }

    @Test
    public void testSave() throws DecoderException, InvalidKeyException {
        System.out.println("测试插入：" + Thread.currentThread().getId());
        UserEntity userEntity = getInstance("张三", "abc123456");
//        userEntityMapper.update(userEntity);
        userEntityMapper.save(userEntity);
    }

    private UserEntity getInstance(String username, String password) throws DecoderException, InvalidKeyException {

        UserEntity entity = new UserEntity();
        entity.setId(IdWorker.getIdStr());
        entity.setUserId("10010");
        entity.setUsername(username);
        entity.setSalt(passwordService.generateKey());

        entity.setPassword(passwordService.encrypt(password, entity.getSalt()));
        entity.setIsDeleted(0);
        return entity;
    }
}
