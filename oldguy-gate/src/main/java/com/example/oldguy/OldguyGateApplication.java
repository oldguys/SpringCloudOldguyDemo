package com.example.oldguy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.oldguy.services")
@EnableDiscoveryClient
@SpringBootApplication
public class OldguyGateApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyGateApplication.class, args);
    }

}
