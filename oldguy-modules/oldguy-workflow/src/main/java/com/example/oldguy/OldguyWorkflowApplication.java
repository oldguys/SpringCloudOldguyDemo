package com.example.oldguy;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: OldguyMultiDatasourceApplication
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0018 上午 11:02
 **/
@MapperScan("com.example.oldguy.modules.workflow.dao.jpas")
@EnableMyBatisGenerator
@EnableDiscoveryClient
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
//        MybatisPlusAutoConfiguration.class
})
public class OldguyWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(OldguyWorkflowApplication.class, args);
    }

}
