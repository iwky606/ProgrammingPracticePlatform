package com.oneq.programmingpracticeplatform;

import com.oneq.programmingpracticeplatform.mapper.UserMapper;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProgrammingPracticePlatformApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        Integer user = userMapper.queryUser("oneq");
        System.out.println(user);
    }

}
