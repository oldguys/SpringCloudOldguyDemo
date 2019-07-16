package com.example.oldguy.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author huangrenhao
 * @date 2018/6/7
 */
//@EnableWebSecurity
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/config/admin").hasRole("admin1")
//                .anyRequest().authenticated()
                .antMatchers("/auth/user/login").anonymous();
//                .and().formLogin();
        http.csrf().disable();
//        http.oauth2ResourceServer().jwt();
    }

    /**
     * 系统内置
     *
     * @return
     */

}
