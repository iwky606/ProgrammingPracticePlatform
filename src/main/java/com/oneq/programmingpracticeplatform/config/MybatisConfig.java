package com.oneq.programmingpracticeplatform.config;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import com.oneq.programmingpracticeplatform.typehandler.enumhandler.AuthTypeHandler;
import com.oneq.programmingpracticeplatform.typehandler.enumhandler.ProblemVisibleHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
