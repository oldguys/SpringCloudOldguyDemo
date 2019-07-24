package com.example.oldguy.model.dao.entities;

import com.example.oldguy.model.annotation.Comment;
import com.example.oldguy.model.annotation.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;

/**
 * @ClassName: UserEntity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/28 0028 上午 9:16
 **/
@Data
@Entity(pre = "sys_", comment = "用户信息表")
public class UserEntity extends CommonBaseEntity {

    /**
     * 用户ID
     */
    @Comment("用户ID")
    @Column(nullable = false, unique = true)
    private String userId;

    /**
     * 用户名
     */
    @Comment("用户名")
    private String username;

    /**
     * 加盐
     */
    @Comment("加盐")
    @JsonIgnore
    private String salt;

    /**
     * 密码
     */
    @Comment("密码")
    @JsonIgnore
    private String password;
}
