package com.oneq.programmingpracticeplatform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    String key();

    int expire();

    TimeUnit timeUnit();

    int randTime() default 60;// 随机expire时间，单位为秒
}