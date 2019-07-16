package com.example.oldguy.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @ClassName: AuthFilterFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/12 0012 下午 3:53
 **/
@Deprecated
public class AuthFilterFactory extends AbstractGatewayFilterFactory<AuthFilterFactory.Config> {

    public AuthFilterFactory(){
        super(Config.class);
        System.out.println();
        System.out.println("AuthFilterFactory 构建授权服务");
        System.out.println();
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }
}
