package com.example.oldguy.services;


import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.utils.Log4jUtils;

/**
 * @ClassName: UserSessionUtils
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 上午 11:58
 **/
public class UserSessionUtils {

    private static final ThreadLocal<JwtInfo> jwtInfoThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<UserEntity> userThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> jwtInfoStrThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    public static UserEntity getUserEntity() {
        return userThreadLocal.get();
    }

    public static String getToken() {
        return tokenThreadLocal.get();
    }

    public static JwtInfo getJwtInfo() {
        return jwtInfoThreadLocal.get();
    }

    public static void pushUserEntity(UserEntity entity) {
        userThreadLocal.set(entity);
    }

    public static void pushToken(String token) {
        tokenThreadLocal.set(token);
    }

    public static void pushJwtToken(String jwtInfoStr) {
        jwtInfoStrThreadLocal.set(jwtInfoStr);
    }

    public static void pushJwtInfo(JwtInfo info) {

        if (null != info) {
            jwtInfoThreadLocal.set(info);
            UserEntity user = new UserEntity();
            user.setUserId(info.getUserId());
            user.setUsername(info.getUsername());
            userThreadLocal.set(user);
        }
    }

    public static void remove() {
        userThreadLocal.remove();
        tokenThreadLocal.remove();
        jwtInfoThreadLocal.remove();
        jwtInfoStrThreadLocal.remove();
        Log4jUtils.getInstance(UserSessionUtils.class).debug("清除session token信息");
    }
}
