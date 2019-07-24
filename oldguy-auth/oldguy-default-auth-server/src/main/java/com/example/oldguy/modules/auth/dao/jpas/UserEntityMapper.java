package com.example.oldguy.modules.auth.dao.jpas;

import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.modules.commons.dao.jpas.BaseEntityMapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserEntityMapper
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/28 0028 上午 10:47
 **/
@Repository
public interface UserEntityMapper extends BaseEntityMapper<UserEntity> {

    /**
     *
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);

    /**
     *  获取用户信息
     * @param userId
     * @return
     */
    UserEntity findByUserId(String userId);
}
