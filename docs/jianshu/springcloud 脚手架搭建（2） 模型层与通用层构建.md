> 微服务项目，每一个项目模块之间都存在相互关联的关系（如：通用的dao，dto，vo，对外暴露的服务api等），而这些关系就会导致项目之间相互彼此jar，而每个jar由于业务功能的关系，又会依赖于很多jar（如依赖 spring-data-jpa ，导致引用很多hibernate等相关jar，剔除又可能导致编译失败）。因此，需要将项目进行模型层抽象
>  github [https://github.com/oldguys/SpringCloudOldguyDemo](https://github.com/oldguys/SpringCloudOldguyDemo)
> 

![单个服务模型依赖关系](https://upload-images.jianshu.io/upload_images/14387783-26f51949baec6324.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![服务间依赖关系](https://upload-images.jianshu.io/upload_images/14387783-21b2f8861e09fed3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![01.png](https://upload-images.jianshu.io/upload_images/14387783-bc14b41ae75768ba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. module 依赖 module-api
2. module-api 中 又相互依赖 关系：
      - module-common-api
            - module-auth-api
            - module-base-api
            - module-log-api
            - module-workflow-api
3. 项目中模块包结构：
    ![02.png](https://upload-images.jianshu.io/upload_images/14387783-8e0851f7bcf707e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

4. 在模型层中定义的接口，可以直接实现到业务层的controller

![03.png](https://upload-images.jianshu.io/upload_images/14387783-1e4e891eb541180e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![04.png](https://upload-images.jianshu.io/upload_images/14387783-42c96565f41a97f7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

5. 模型层都是业务实体和业务接口，但是有时候，有很多通用功能，都需要被多个项目引入（如：我们经常用到的jar：jpa，mybatis 等），而这些又不是每个服务都需要用到，此时，可以抽象多 一层 client层，用于编写通用可引入配置

![05.png](https://upload-images.jianshu.io/upload_images/14387783-2d8941e4d3cfced3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>oldguy-clients</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <!--<packaging>pom</packaging>-->

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-auth-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-auth-client</name>
    <description>Demo project for Spring Boot</description>

    <dependencies>
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-auth-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.8.RELEASE</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-core</artifactId>
        </dependency>

    </dependencies>

</project>

```

此时就可以在这个模块 （auth-client ）编写通用的配置，如：

![06.png](https://upload-images.jianshu.io/upload_images/14387783-1ab7f2278a675bed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

编写类似于 spring那种 @EnableAuthClient 注解，引入此jar，在配置类添加此注解，就可以自动开启配置

```
/**
 * @ClassName: EnableAuthClient
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:00
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoAuthClientConfiguration.class)
public @interface EnableAuthClient {

}

package com.example.oldguy.configs;

import com.example.oldguy.interceptors.FeignInterceptor;
import com.example.oldguy.interceptors.TokenInterceptor;
import com.example.oldguy.utils.Log4jUtils;
import com.example.oldguy.utils.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * @ClassName: AutoAuthClientConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 8:06
 **/

@ComponentScan("com.example.oldguy.services")
public class AutoAuthClientConfiguration {

//    private String configPath = "classpath:auth-client.properties";
    private String configPath = "auth-client.properties";

    public static String JWT_INFO_FLAG = "oldguy.jwt-info-header";

    public static String JWT_INFO_NAME = "jwt-info";

    @PostConstruct
    public void init() {

        Log4jUtils.getInstance(AutoAuthClientConfiguration.class).debug("启动 auth-client");

        Properties properties = PropertiesUtils.getProperties(configPath);
        Object value = properties.getProperty(JWT_INFO_FLAG);
        if (null != value) {
            String jwtName = String.valueOf(value);
            if (StringUtils.isNotBlank(jwtName)) {
                JWT_INFO_NAME = String.valueOf(value);
            }
        }
    }

    @Bean
    public TokenInterceptor tokenInterceptor() {
        Log4jUtils.getInstance(AutoAuthClientConfiguration.class).debug("开启 token 解析");
        return new TokenInterceptor();
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        Log4jUtils.getInstance(AutoAuthClientConfiguration.class).debug("开启 feign token 转发");
        return new FeignInterceptor();
    }

    @Bean
    public InterceptorConfiguration interceptorConfig() {
        return new InterceptorConfiguration();
    }

}

```
实际微服务

![07.png](https://upload-images.jianshu.io/upload_images/14387783-7dfd183563cda0f3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


同理，权限配置也是类似。





