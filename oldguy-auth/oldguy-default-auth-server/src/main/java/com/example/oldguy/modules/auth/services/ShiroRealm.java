package com.example.oldguy.modules.auth.services;/**
 * Created by Administrator on 2018/10/17 0017.
 */

import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.modules.auth.dao.jpas.UserEntityMapper;
import com.example.oldguy.services.UserSessionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/17 0017 11:29
 */
@Service
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserEntityMapper userEntityMapper;


    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<>();
        roleSet.add("test");
        info.setRoles(roleSet);
        return info;
    }

    /**
     * 登录认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String principal = (String) token.getPrincipal();
        System.out.println("登录校验:" + principal);

        UserEntity entity = userEntityMapper.findByUserId(principal);

        if (null == entity) {
            throw new UnknownAccountException("未找到该用户！");
        }
        String password = entity.getPassword();
//        Sha256Hash hash = new Sha256Hash(entity.getSalt());
        MySaltHash hash = new MySaltHash(entity.getSalt());

        entity.setPassword(null);
        entity.setSalt(null);
        UserSessionUtils.pushUserEntity(entity);

        System.out.println("获取到用户进行校验....");
        return new SimpleAuthenticationInfo(principal,password , hash, getName());
    }
}
