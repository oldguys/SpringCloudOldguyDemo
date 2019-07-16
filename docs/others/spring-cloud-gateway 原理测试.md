#### spring-cloud-gateway 原理测试

**必经FilteringWebHandler**
org.springframework.cloud.gateway.handler.FilteringWebHandler

gateway默认必经过滤器加载
```
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

默认情况下，经过gateway的微服务都可以按照以下格式走通

```
// serverId 为服务名，默认为spring.appliaction.name
{serverId}/.......  
例如:
微服务 a 路径: /test 微服务注册名（spring.appliaction.name）为 test-a
经过gateway则为 test-a/test
```

**StripPrefixGatewayFilter**

如果希望 使用 /api/{server-id}/.... 作为服务前缀时，需要使用过滤器 StripPrefixGatewayFilter

org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory

根据分割符号‘/’进行阶段 part 参数 为阶段去除个数

可以直接在环境变量配置 

```
- id: oldguy-base
  uri: lb://oldguy-base
  predicates:
    - Path=/api/base/**
  filters:
    - StripPrefix=2 // 配置拦截器
```

**自定义GlobalFilter**


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

PS: StripPrefixGatewayFilter order = 1 



