package com.example.oldguy.configs.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.example.oldguy.config.AbstractMybatisPlusConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;

/**
 * @ClassName: Test1DataSource
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 下午 1:55
 **/
@MapperScan(basePackages = {
        "com.example.oldguy.modules.test2.dao.jpas"
},
        sqlSessionTemplateRef = "test2SqlSessionTemplate",
        sqlSessionFactoryRef = "test2SqlSessionFactory")
@Configuration
public class Test2DataSource extends AbstractMybatisPlusConfiguration {

    @Primary
    @Bean(name = "test2DruidDataSource")
    @ConfigurationProperties(prefix = "test2.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "test2MybatisPlusProperties")
    @ConfigurationProperties(prefix = "mybatis-plus.test2")
    public MybatisPlusProperties mybatisPlusProperties() {
        return new MybatisPlusProperties();
    }


    @Bean(name = "test2SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("test2DruidDataSource") DruidDataSource dataSource,
                                                   @Qualifier("test2MybatisPlusProperties") MybatisPlusProperties properties,
                                                   ResourceLoader resourceLoader,
                                                   ApplicationContext applicationContext) throws Exception {
        return getSqlSessionFactory(dataSource,
                properties,
                resourceLoader,
                null,
                null,
                applicationContext);
    }

    @Bean(name = "test2SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test2MybatisPlusProperties") MybatisPlusProperties properties,
                                                 @Qualifier("test2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(sqlSessionFactory, properties);
    }
}
