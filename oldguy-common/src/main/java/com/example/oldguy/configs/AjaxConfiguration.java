package com.example.oldguy.configs;

import com.example.oldguy.modules.commons.filters.CrosFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: AjaxConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/9/29 0029 下午 4:31
 **/
public class AjaxConfiguration {

    @Bean
    public FilterRegistrationBean registerFilter(){

        FilterRegistrationBean bean = new FilterRegistrationBean();

        bean.addUrlPatterns("/*");
        bean.setFilter(new CrosFilter());

        return bean;
    }

}