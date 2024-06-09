package com.oneq.programmingpracticeplatform;

import com.oneq.programmingpracticeplatform.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class ProgrammingPracticePlatformApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        Integer user = userMapper.queryUser("oneq");
        System.out.println(user);
    }

    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("hello", null);
        Object o = redisTemplate.opsForValue().get("hello");
        // if(o==null){
        System.out.println(o == null);
        // }
    }

}
