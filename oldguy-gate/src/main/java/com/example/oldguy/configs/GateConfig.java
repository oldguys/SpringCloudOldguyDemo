package com.example.oldguy.configs;

import com.example.oldguy.filters.AuthFilterFactory;
import com.example.oldguy.services.AuthClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @ClassName: GateConfig
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 7:54
 **/
@Configuration
public class GateConfig {

//    @Bean
    public AuthFilterFactory authFilterFactory(){
        return new AuthFilterFactory();
    }

//    @Autowired
//    private AuthClientApi AuthClientApi;

//    @Bean
//    public GlobalFilter a() {
//        return (exchange, chain) -> {
//            System.out.println("first pre filter");
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
////                log.info("third post filter");
//                System.out.println("third post filter");
//            }));
//        };
//    }



}
