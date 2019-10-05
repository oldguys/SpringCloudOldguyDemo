> 微服务项目，大部分都是基于token进行认证的，本处主要使用的是JWT作为用户数据传递
>    github [https://github.com/oldguys/SpringCloudOldguyDemo](https://github.com/oldguys/SpringCloudOldguyDemo)
>


调用原理：
1）. 请求经过网关过滤，将 token从请求头获取，并调用 微服务 auth-server 进行校验token有效性，如果无效，直接返回。
2）. 有效请求经过拦截器分发到微服务，并附带解析好的token信息
3)  . 在微服务端利用拦截器从请求头获取用户信息，配置到ThreadLocal中，作为缓存。
4）.当微服务调用微服务时，利用feign的 拦截器对请求头再次补充，使得微服务间的请求头也可以获取到 3）中的 用户信息



Step1： 网关拦截，权限校验

1. 网关 spring-gateway
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.example.oldguy</groupId>
        <artifactId>spring-cloud-oldguy</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>

    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-gate</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>oldguy-gate</name>
    <description>Demo project for Spring Boot</description>

    <dependencies>
      <!-- 网关基本依赖 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
        <!-- 注册中心 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>${nacos.version}</version>
        </dependency>
        <!-- 用户认证微服务 api -->
        <dependency>
            <groupId>com.example.oldguy</groupId>
            <artifactId>oldguy-auth-api</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
```

2. 属性文件 application-router.yml
``````
spring:
  cloud:
    gateway:
#      globalcors:
#        corsConfigurations:
#          '[/**]':
#            allowedOrigins: "*"
#            allowedMethods:  "*"
      discovery:
        locator:
          lowerCaseServiceId: true
          enabled: true
#          routeIdPrefix: /api/
      routes:
        - id: auth-server
          uri: lb://auth-server
          predicates:
            - Path=/api/auth/**
          filters:
            - StripPrefix=2
        - id: oldguy-base
          uri: lb://oldguy-base
          predicates:
            - Path=/api/base/**
          filters:
#            - PrefixPath=/api/base/
            - StripPrefix=2

``````
3. 配置 权限控制过滤器
```
package com.example.oldguy.filters;

import com.alibaba.fastjson.JSON;
import com.example.oldguy.constants.FilterConstants;
import com.example.oldguy.exceptions.TokenException;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.model.dto.RspMsg;
import com.example.oldguy.services.AuthClientApi;
import com.example.oldguy.utils.Log4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @ClassName: AuthFilter
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/12 0012 上午 11:33
 **/
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Value("${oldguy.no-auth-filter}")
    private String noAuthFilterUrl;
    @Value("${oldguy.token-name}")
    private String tokenName;
    @Value("${oldguy.jwt-info-header}")
    private String jwtInfoHeader;

    @Autowired
    private AuthClientApi authClientApi;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        RequestPath path = request.getPath();

        if (path.toString().startsWith(noAuthFilterUrl)) {

            Log4jUtils.getInstance(getClass()).info("不需要token校验");
            return chain.filter(exchange);
        }

        List<String> list = request.getHeaders().get(tokenName);

        if (null == list || list.isEmpty()) {
            throw new RuntimeException("请求头不存在token:[" + tokenName + "]");
        }

        String token = list.get(0);
        if (StringUtils.isNotBlank(token)) {

            RspMsg<JwtInfo> rsp = authClientApi.checkToken(token);

            if (rsp.getCode().equals(HttpStatus.OK.value())) {
                JwtInfo info = rsp.getData();
                String baseB4Jwt = Base64Utils.encodeToString(JSON.toJSONString(info).getBytes());
                ServerHttpRequest newRequest = request.mutate().header(jwtInfoHeader, baseB4Jwt).build();

                return chain.filter(exchange.mutate().request(newRequest).build());
            }

            throw new TokenException(rsp.getCode(), rsp.getMessage());
        }
        throw new TokenException(HttpStatus.BAD_REQUEST.value(), "请求不具备有效token信息！");
    }

    @Override
    public int getOrder() {
        return FilterConstants.AUTH_ORDER;
    }
}

/**
 * @ClassName: FilterConstants
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/12 0012 下午 2:42
 **/
public interface FilterConstants {

    /**
     *  授权拦截
     */
    int AUTH_ORDER = 0;
}

```
以上基本完成gateway配置权限控制

-----

下面描述大概springcloud-gateway的原理

1. gateway必经 FilteringWebHandler org.springframework.cloud.gateway.handler.FilteringWebHandler 

PS: 从 springcloud-gateway源码中截取，由下面可以看出，都是基于Order进行过滤器的顺序的,所以需要实现上面的Order接口才能配置过滤顺序
```
// 从 springcloud-gateway源码中截取，由下面可以看出，都是基于Order进行过滤器的顺序的
	private static List<GatewayFilter> loadFilters(List<GlobalFilter> filters) {
		return filters.stream().map(filter -> {
			GatewayFilterAdapter gatewayFilter = new GatewayFilterAdapter(filter);
			if (filter instanceof Ordered) {
				int order = ((Ordered) filter).getOrder();
				return new OrderedGatewayFilter(gatewayFilter, order);
			}
			return gatewayFilter;
		}).collect(Collectors.toList());
	}
```

2. gateway进行url分发，默认的分发方式是 mirco-server的名称+路径，作为默认调用，如：
![注册中心 注册的微服务名称](https://upload-images.jianshu.io/upload_images/14387783-6a7c2c55152bdb19.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![不带token](https://upload-images.jianshu.io/upload_images/14387783-a95cee3ab44f7321.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![带token](https://upload-images.jianshu.io/upload_images/14387783-49c05e2844644ac2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3. 如果需要进行url特殊格式化，可以想上面配置文件  application-router.yml 一样配置 如：
默认： 127.0.0.1:9000/auth-server/auth/login 可以映射为 127.0.0.1:9000/api/auth/auth/login
其中 StripPrefix 为过滤器 StripPrefixGatewayFilter
```
        - id: auth-server
          uri: lb://auth-server
          predicates:
            - Path=/api/auth/**
          filters:
            - StripPrefix=2
```
来源：
org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory

默认： StripPrefixGatewayFilter order = 1，所以 之前配置的 token 校验过滤器 可以根据需要 配置在此处的 前或者后
```
- StripPrefix=2
```
意思为 截取分隔符 part = "/" , 2 为缩减 2个 所以 api： /api/auth/auth/login 会被截取为 auth/login 并且映射到 auth-server 去寻找。

---

Step2：Token在微服务间传递 

实现 feign的拦截器： RequestInterceptor ，将解析好的 Token 在保存在请求头中再发出
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
Step3：Token在作为静态常量调用

1. 基于 ThreadLocal 封装好 UserSessionUtils 作为信息的存储
```
package com.example.oldguy.services;


import com.example.oldguy.model.dao.entities.UserEntity;
import com.example.oldguy.model.dto.JwtInfo;
import com.example.oldguy.utils.Log4jUtils;

/**
 * @ClassName: UserSessionUtils
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/8 0008 上午 11:58
 **/
public class UserSessionUtils {

    private static final ThreadLocal<JwtInfo> jwtInfoThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<UserEntity> userThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> jwtInfoStrThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> tokenThreadLocal = new ThreadLocal<>();

    public static UserEntity getUserEntity() {
        return userThreadLocal.get();
    }

    public static String getToken() {
        return tokenThreadLocal.get();
    }

    public static JwtInfo getJwtInfo() {
        return jwtInfoThreadLocal.get();
    }

    public static String getJwtToken() {
        return jwtInfoStrThreadLocal.get();
    }

    public static void pushUserEntity(UserEntity entity) {
        userThreadLocal.set(entity);
    }

    public static void pushToken(String token) {
        tokenThreadLocal.set(token);
    }

    public static void pushJwtToken(String jwtInfoStr) {
        jwtInfoStrThreadLocal.set(jwtInfoStr);
    }

    public static void pushJwtInfo(JwtInfo info) {

        if (null != info) {
            jwtInfoThreadLocal.set(info);
            UserEntity user = new UserEntity();
            user.setUserId(info.getUserId());
            user.setUsername(info.getUsername());
            userThreadLocal.set(user);
        }
    }

    public static void remove() {
        userThreadLocal.remove();
        tokenThreadLocal.remove();
        jwtInfoThreadLocal.remove();
        jwtInfoStrThreadLocal.remove();
        Log4jUtils.getInstance(UserSessionUtils.class).debug("清除session token信息");
    }
}

```

2.  利用拦截器 在请求头中将结果进行解析 转换为对象 推进 UserSessionUtils 
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

3. 注意以上拦截器需要注册 到spirng容器 ，前一章节讲到 使用 client端模式，将主要注册的通用 组件工具抽象，然后到 mirco-app中直接引用，并利用 注解开启。
```
package com.example.oldguy;

import com.example.oldguy.configs.AutoAuthClientConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

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
以上基本完成整套token在 微服务调用的过程