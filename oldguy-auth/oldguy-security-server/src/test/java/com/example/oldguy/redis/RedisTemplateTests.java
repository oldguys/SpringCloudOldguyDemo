package com.example.oldguy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: RedisTemplateTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/6/29 0029 下午 3:06
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTests {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void test(){


    }
}
