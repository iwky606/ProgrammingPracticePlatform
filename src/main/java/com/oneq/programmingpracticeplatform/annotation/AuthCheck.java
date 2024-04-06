package com.oneq.programmingpracticeplatform.annotation;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    AuthEnum mustRole();
}

