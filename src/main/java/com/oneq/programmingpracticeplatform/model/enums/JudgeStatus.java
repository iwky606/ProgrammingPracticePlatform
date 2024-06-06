package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public enum JudgeStatus implements BaseEnum {
    NORMAL(0), ACCEPT(1), WRONG_ANSWER(2), COMPILE_ERROR(3), TIME_LIMIT_EXCEED(4), MEMORY_LIMIT_EXCEED(5), RUNTIME_ERROR(6), OTHER_ERROR(7);
    private final int value;

    JudgeStatus(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}