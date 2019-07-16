#### GateWay 拦截

> 用于SpringCloud项目的路由，请求转发。
>

###### spring-cloud-gateway 使用

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
    <version>2.1.1.RELEASE</version>
</dependency>
```

配置gateway 环境变量

SpringCloudOldguyDemo\oldguy-gate\src\main\resources\application-router.yml
```
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

```


###### auth-api 客户端

引入maven
```
<dependency>
    <groupId>com.example.oldguy</groupId>
    <artifactId>oldguy-auth-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

配置环境变量 appliaction.yml
```
oldguy:
  no-auth-filter: /api/auth/
  token-name: auth-token
  jwt-info-header: jwt-info
```

配置权限拦截器
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

```

