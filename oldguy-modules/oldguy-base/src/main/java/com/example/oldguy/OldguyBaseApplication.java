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
