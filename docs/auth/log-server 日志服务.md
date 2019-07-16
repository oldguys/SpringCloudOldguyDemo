#### log-server 日志服务

**log-server**

1. 使用webflux 增加系统吞吐量
2. 使用mongodb 作为日志记录数据库
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-mongodb</artifactId>
    <version>2.1.4.RELEASE</version>
</dependency>
```

**log-client**

使用详情

1. 使用 @EnableLogAspect 开启日志控制

```
package com.example.oldguy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableLogAspect
@EnablePermAspect
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OldguyBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyBaseApplication.class, args);
    }

}

```
 


###### 核心类

1. 自定义日志控制注解

```
package com.example.oldguy.annonations;

import com.example.oldguy.model.constants.AppLogType;

import java.lang.annotation.*;

/**
 * @ClassName: LogControl
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 2:15
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogControl {

    AppLogType type() default AppLogType.CRUD;

    String message();


}

```

2. 基于自定义注解的aop

```
package com.example.oldguy.aop;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.annonations.LogControl;
import com.example.oldguy.model.constans.AspectConstants;
import com.example.oldguy.model.dto.AppLogAddForm;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.services.AppLogApi;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;


/**
 * @ClassName: PermsAspect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 上午 9:01
 **/
@Aspect
@Order(AspectConstants.LOG_ORDER)
public class LogAspect {

    @Autowired
    private AppLogApi appLogApi;

    @Pointcut("@annotation(com.example.oldguy.annonations.LogControl)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogControl logControl = signature.getMethod().getAnnotation(LogControl.class);


        RspMsg rsp = appLogApi.save(
                new AppLogAddForm(
                        logControl.type().getCode(),
                        logControl.message(),
                        null
                )
        );

        System.out.println(JSON.toJSON(rsp));
    }
}

```
