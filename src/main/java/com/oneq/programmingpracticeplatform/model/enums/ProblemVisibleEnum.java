package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public enum ProblemVisibleEnum implements BaseEnum {
    PRIVATE(1), PUBLIC(2), CONTEST(3);

    private final int value;

    ProblemVisibleEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}