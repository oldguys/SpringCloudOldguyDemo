#### Mybatis-plus 配置多数据源

**模块位置：oldguy-multi-datasource**


Step1： 剔除自动装配

```
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
                MybatisPlusAutoConfiguration.class,
        }
)
public class OldguyMultiDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyMultiDatasourceApplication.class, args);
    }

}

```


Step2： 从框架中抽出源码 com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration

![01.jpg](https://upload-images.jianshu.io/upload_images/14387783-2b038f61096aa608.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

根据框架源码编写抽象类
com.example.oldguy.configs.AbstractMybatisPlusConfiguration
```
package com.example.oldguy.configs;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @ClassName: AbstractMybatisPlusConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/18 0018 上午 11:02
 **/
public class AbstractMybatisPlusConfiguration {

    protected SqlSessionFactory getSqlSessionFactory(
            DataSource dataSource,
            MybatisPlusProperties properties,
            ResourceLoader resourceLoader,
            Interceptor[] interceptors,
            DatabaseIdProvider databaseIdProvider,
            ApplicationContext applicationContext
    ) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(properties.getConfigLocation())) {
            factory.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));
        }
        applyConfiguration(factory, properties);
        if (properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }
        if (!ObjectUtils.isEmpty(interceptors)) {
            factory.setPlugins(interceptors);
        }
        if (databaseIdProvider != null) {
            factory.setDatabaseIdProvider(databaseIdProvider);
        }
        if (StringUtils.hasLength(properties.getTypeAliasesPackage())) {
            factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        }
        // TODO 自定义枚举包
        if (StringUtils.hasLength(properties.getTypeEnumsPackage())) {
            factory.setTypeEnumsPackage(properties.getTypeEnumsPackage());
        }
        if (properties.getTypeAliasesSuperType() != null) {
            factory.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
        }
        if (StringUtils.hasLength(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(properties.resolveMapperLocations())) {
            factory.setMapperLocations(properties.resolveMapperLocations());
        }
        // TODO 此处必为非 NULL
        GlobalConfig globalConfig = properties.getGlobalConfig();
        //注入填充器
        if (applicationContext.getBeanNamesForType(MetaObjectHandler.class,
                false, false).length > 0) {
            MetaObjectHandler metaObjectHandler = applicationContext.getBean(MetaObjectHandler.class);
            globalConfig.setMetaObjectHandler(metaObjectHandler);
        }
        //注入主键生成器
        if (applicationContext.getBeanNamesForType(IKeyGenerator.class, false,
                false).length > 0) {
            IKeyGenerator keyGenerator = applicationContext.getBean(IKeyGenerator.class);
            globalConfig.getDbConfig().setKeyGenerator(keyGenerator);
        }
        //注入sql注入器
        if (applicationContext.getBeanNamesForType(ISqlInjector.class, false,
                false).length > 0) {
            ISqlInjector iSqlInjector = applicationContext.getBean(ISqlInjector.class);
            globalConfig.setSqlInjector(iSqlInjector);
        }
        factory.setGlobalConfig(globalConfig);
        return factory.getObject();
    }

    private void applyConfiguration(MybatisSqlSessionFactoryBean factory, MybatisPlusProperties properties) {
        MybatisConfiguration configuration = properties.getConfiguration();
        if (configuration == null && !StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new MybatisConfiguration();
        }
//        if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
//            for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
//                customizer.customize(configuration);
//            }
//        }
        factory.setConfiguration(configuration);
    }


    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, MybatisPlusProperties properties) {
        ExecutorType executorType = properties.getExecutorType();
        if (executorType != null) {
            return new SqlSessionTemplate(sqlSessionFactory, executorType);
        } else {
            return new SqlSessionTemplate(sqlSessionFactory);
        }
    }

}

```
Step3：根据抽象类写实现类

数据库1：com.example.oldguy.configs.Demo1Configuration

```
package com.example.oldguy.configs.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
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
        "com.example.oldguy.modules.test1.dao.jpas"
},
        sqlSessionTemplateRef = "test1SqlSessionTemplate",
        sqlSessionFactoryRef = "test1SqlSessionFactory")
@Configuration
public class Test1DataSource extends AbstractMybatisPlusConfiguration {

//    @Primary
    @Bean(name = "test1DruidDataSource")
    @ConfigurationProperties(prefix = "test1.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "test1MybatisPlusProperties")
    @ConfigurationProperties(prefix = "mybatis-plus.test1")
    public MybatisPlusProperties mybatisPlusProperties() {
        return new MybatisPlusProperties();
    }


    @Bean(name = "test1SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("test1DruidDataSource") DruidDataSource dataSource,
                                                   @Qualifier("test1MybatisPlusProperties") MybatisPlusProperties properties,
                                                   ResourceLoader resourceLoader,
                                                   ApplicationContext applicationContext) throws Exception {
        return getSqlSessionFactory(dataSource,
                properties,
                resourceLoader,
                null,
                null,
                applicationContext);
    }

    @Bean(name = "test1SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("test1MybatisPlusProperties") MybatisPlusProperties properties,
                                                 @Qualifier("test1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return getSqlSessionTemplate(sqlSessionFactory, properties);
    }
}


```

数据库2：com.example.oldguy.configs.Demo2Configuration
```
package com.example.oldguy.configs.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
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

```

Step4：配置yml文件

```
test1:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/oldguy-demo-multi1?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
test2:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/oldguy-demo-multi2?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver

mybatis-plus:
  test1:
    config-location: classpath:configs/myBatis-config.xml
    mapper-locations: classpath:mappers/test1/**/*.xml
    type-aliases-package: com.example.oldguy.modules.test1.dao.entities
    global-config:
      db-config:
        id-type: UUID
  test2:
    config-location: classpath:configs/myBatis-config.xml
    mapper-locations: classpath:mappers/test2/**/*.xml
    type-aliases-package: com.example.oldguy.modules.test2.dao.entities
    global-config:
      db-config:
        id-type: UUID
```







