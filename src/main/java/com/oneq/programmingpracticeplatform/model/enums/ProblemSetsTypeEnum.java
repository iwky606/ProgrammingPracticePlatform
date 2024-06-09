package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public enum ProblemSetsTypeEnum implements BaseEnum {
    GENERAL(0), CONTEST(1);

    private final int value;

    ProblemSetsTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return 0;
    }
}
