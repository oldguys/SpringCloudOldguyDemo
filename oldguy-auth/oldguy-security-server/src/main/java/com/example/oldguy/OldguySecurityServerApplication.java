package com.example.oldguy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan(basePackages = "com.example.oldguy.modules.auth.dao.jpas")
@EnableTransactionManagement
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.example.oldguy")
public class OldguySecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguySecurityServerApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
