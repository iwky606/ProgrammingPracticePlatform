package com.oneq.programmingpracticeplatform;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProgrammingPracticePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgrammingPracticePlatformApplication.class, args);
    }

    @Bean
    public MessageConverter jacksonMessageConvertor() {
        return new Jackson2JsonMessageConverter();
    }

}
