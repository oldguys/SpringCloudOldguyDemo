package com.example.oldguy;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @ClassName: OldguyMultiDatasourceApplication
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/17 0018 上午 11:02
 **/
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
