package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public enum Language implements BaseEnum {
    JAVA8(0), JAVA17(1), CPP20(2), PYTHON3(3);

    private final int value;

    Language(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
