package com.example.oldguy.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author huangrenhao
 * @date 2019/1/10
 */
@Configuration
public class DemoConfiguration {

    @Qualifier("propertiesConfiguration")
    @Autowired
    private PropertiesConfiguration propertiesConfiguration;
    @Qualifier("DBConfiguration")
    @Autowired
    private DBConfiguration dbConfiguration;

    @PostConstruct
    public void init() throws IOException {
        // 初始化属性配置
        propertiesConfiguration.init();
        // 初始化 数据库配置
        dbConfiguration.init();

    }
}
