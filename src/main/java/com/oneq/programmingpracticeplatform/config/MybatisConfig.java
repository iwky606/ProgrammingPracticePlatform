package com.oneq.programmingpracticeplatform.config;

import com.oneq.programmingpracticeplatform.typehandler.AuthTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {
    @Bean
    public AuthTypeHandler authTypeHandler() {
        return new AuthTypeHandler();
    }
}
