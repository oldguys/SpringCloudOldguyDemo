package com.example.oldguy.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName: RedisConfig
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/29 0029 上午 11:17
 **/
@Configuration
public class RedisConfiguration {

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory, StringRedisSerializer serializer){
        StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
}
