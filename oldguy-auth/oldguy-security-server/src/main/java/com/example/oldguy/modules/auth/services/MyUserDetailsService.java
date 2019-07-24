package com.example.oldguy.modules.auth.services;

import com.example.oldguy.model.workflow.dao.entities.UserEntity;
import com.example.oldguy.modules.auth.dao.jpas.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @ClassName: MyUserDetailsService
 * @Author: ren
 * @Description: 加载用户信息
 * @CreateTIme: 2019/6/28 0028 下午 3:24
 **/
@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        System.out.println(this.getClass().getName() + ":" + s);

        UserEntity entity = userEntityMapper.findByUserId(s);

        if (null == entity) {
            throw new UsernameNotFoundException("没有找到该用户！请确定用户名是否正确！");
        }

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_normal"));

        User user = new User(entity.getUsername(), entity.getPassword(), authorities);

        return user;
    }
}
