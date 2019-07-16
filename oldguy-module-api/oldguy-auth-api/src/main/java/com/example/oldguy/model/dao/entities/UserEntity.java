package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @ClassName: UserEntity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/28 0028 上午 9:16
 **/
@Data
@Entity(pre = "sys_")
public class UserEntity extends CommonBaseEntity {

    /**
     *  用户ID
     */
    private String userId;

    /**
     *  用户名
     */
    private String username;

    /**
     *  加盐
     */
    @JsonIgnore
    private String salt;

    /**
     *  密码
     */
    @JsonIgnore
    private String password;
}
