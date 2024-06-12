package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public enum ProblemVisibleEnum implements BaseEnum {
    PRIVATE(0), PUBLIC(1), CONTEST(2);

    private final int value;

    ProblemVisibleEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
