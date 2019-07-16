package com.example.oldguy.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author huangrenhao
 * @date 2019/1/9
 */
@Configuration
public class DBConfiguration {

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Autowired
    private DataSource dataSource;
    @Qualifier("dbRegisterConfiguration")
    @Autowired
    private DbRegisterConfiguration dbRegisterConfiguration;

    public void init() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dbRegisterConfiguration.initDB(jdbcTemplate, typeAliasesPackage);
    }
}
