package com.example.oldguy;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: OldguyMultiDatasourceApplication
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0018 上午 11:02
 **/
@EnableFeignClients
@EnableDiscoveryClient
@EnableLogAspect
@EnablePermAspect
@EnableAuthClient
@EnableMyBatisGenerator
@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class,
                MybatisPlusAutoConfiguration.class,
        }
)
public class OldguyMultiDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyMultiDatasourceApplication.class, args);
    }

}
