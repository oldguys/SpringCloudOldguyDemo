package com.example.oldguy.model.constans;

/**
 * @ClassName: JwtInfoConstants
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 下午 2:26
 **/
public interface JwtInfoConstants {

    /**
     *  用户ID
     */
    String USER_ID = "userId";

    /**
     *  用户名
     */
    String USERNAME = "username";


    /**
     * 加密算法 可以抽象到环境变量中配置
     */
    String MAC_NAME = "HMacSHA256";
}
