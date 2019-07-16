package com.example.oldguy.configs;

import com.example.oldguy.modules.auth.services.MyCredentialsMatcher;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/4 0004 下午 9:50
 **/
@Configuration
public class ShiroConfiguration {



//    @Bean
//    public DefaultPasswordService passwordService() {
//        DefaultPasswordService passwordService = new DefaultPasswordService();
//        return passwordService;
//    }

//    @Bean
//    public SimpleCredentialsMatcher HashedCredentialsMatcher() {
//
//        SimpleCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-256");
//
//        return credentialsMatcher;
//    }

    /**
     * Shiro核心处理类，所有的类都围绕着这个处理器进行执行
     *
     * @param realm
     * @return
     */
    @Bean
    public DefaultSecurityManager securityManager(AuthorizingRealm realm, MyCredentialsMatcher credentialsMatcher) {

        // 设置 密码校验器
        realm.setCredentialsMatcher(credentialsMatcher);

        DefaultSecurityManager securityManager = new DefaultSecurityManager(realm);
        // 注入到全局变量，是的SecurityUtils可以使用静态方法来调用
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }


    /**
     * 生命周期维护容器
     * 必须在其他4个容器之后注入。不然会抛异常。
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启注解式 权限拦截
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
        bean.setProxyTargetClass(true);
        return bean;
    }

    /**
     * 开启注解式 权限拦截
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }


}
