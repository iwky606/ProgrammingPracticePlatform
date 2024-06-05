package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public enum AuthEnum implements BaseEnum {
    ADMIN(1), STUDENT(2), TEACHER(3);

    private final int value;

    AuthEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
