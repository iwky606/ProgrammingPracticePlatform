package com.oneq.programmingpracticeplatform.config;

import com.oneq.programmingpracticeplatform.typehandler.enumhandler.AuthTypeHandler;
import com.oneq.programmingpracticeplatform.typehandler.enumhandler.ProblemVisibleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {
    @Bean
    public AuthTypeHandler authTypeHandler() {
        return new AuthTypeHandler();
    }

    @Bean
    public ProblemVisibleHandler problemVisibleHandler() {
        return new ProblemVisibleHandler();
    }
}
