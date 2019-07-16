package com.example.oldguy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAuthClient
@MapperScan(basePackages = "com.example.oldguy.modules.auth.dao.jpas")
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.example")
public class OldguyDefaultAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyDefaultAuthServerApplication.class, args);
    }

}
