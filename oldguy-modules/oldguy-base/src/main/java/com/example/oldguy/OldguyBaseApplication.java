package com.example.oldguy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: AppDictionary
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0017 上午 9:07
 **/
@MapperScan("com.example.oldguy.modules.dictionary.dao.jpas")
@EnableTransactionManagement
@EnableMyBatisGenerator
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
