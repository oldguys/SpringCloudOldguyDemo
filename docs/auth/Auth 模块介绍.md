##### Auth 模块

模块：
1. auth-server
2. auth-client

**auth-server**

---
描述：用于 用户 认证与授权

>
> 1. 用户登录入口
> 2. token生成,校验,解析入口
> 3. 权限校验
>

引入 jwt jar
```
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.4.0</version>
</dependency>
```
用户token 生成,解析,校验

引入 shiro jar
```
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-core</artifactId>
    <version>${shiro.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>${shiro.version}</version>
</dependency>
```

**auth-client**

---

用于 各个系统集成 auth-server,进行对接

>
> 1. 接入客户端
> 2. token解析，feign token 传递
> 3. 权限校验
>

使用:
1. 引入 Maven
```
<dependency>
    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-log-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
2. 在启动类开启

> 
> @EnableAuthClient : 开启用户认证
>
> @EnablePermAspect : 开启权限控制 (依赖于@EnableAuthClient,引入时不需要引入  @EnableAuthClient)
>

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

功能代码:

com.example.oldguy.interceptors.TokenInterceptor : token 解析拦截器
```
package com.example.oldguy.interceptors;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.configs.AutoAuthClientConfiguration;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.services.UserSessionUtils;
import com.example.oldguy.utils.Log4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: TokenInterceptor
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 3:05
 **/
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AutoAuthClientConfiguration.JWT_INFO_NAME);
        if (StringUtils.isNotBlank(token)) {
            Log4jUtils.getInstance(getClass()).debug("解析token");

            byte[] decode = Base64Utils.decodeFromString(token);
            JwtInfo info = JSON.parseObject(new String(decode, "UTF-8"), JwtInfo.class);

            UserSessionUtils.pushJwtToken(token);
            UserSessionUtils.pushJwtInfo(info);

        } else {
            Log4jUtils.getInstance(getClass()).warn("没有获取到token信息");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserSessionUtils.remove();
    }
}

```

com.example.oldguy.interceptors.FeignInterceptor : feign解析 拦截器
```
package com.example.oldguy.interceptors;

import com.example.oldguy.configs.AutoAuthClientConfiguration;
import com.example.oldguy.services.UserSessionUtils;
import com.example.oldguy.utils.Log4jUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @ClassName: FeignInterceptor
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/15 0015 下午 1:44
 **/
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        Log4jUtils.getInstance(getClass()).debug("feign 转发 token");
        template.header(AutoAuthClientConfiguration.JWT_INFO_NAME, UserSessionUtils.getJwtToken());
    }
}

```


