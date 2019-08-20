package com.example.oldguy.configs;

import com.example.oldguy.EnableMyBatisGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName: TestConfiguration
 * @Author: ren
 * @Description: 配置测试环境时候启动的环境变量
 * @CreateTIme: 2019/7/26 0026 上午 10:27
 **/
@EnableMyBatisGenerator
@ConditionalOnExpression("'${spring.profiles.active}'.equals('test')")
@Configuration
public class TestConfiguration {
}
