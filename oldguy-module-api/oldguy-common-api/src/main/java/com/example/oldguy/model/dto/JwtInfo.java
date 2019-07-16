package com.example.oldguy.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: JwtInfo
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/29 0029 上午 10:45
 **/
@Data
public class JwtInfo implements Serializable {

    /**
     *  用户ID
     */
    private String userId;

    /**
     *  用户姓名
     */
    private String username;

    /**
     *  过期时间
     */
    private Date expireTime;

}
