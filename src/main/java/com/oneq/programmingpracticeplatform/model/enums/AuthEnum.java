package com.oneq.programmingpracticeplatform.model.enums;

public enum AuthEnum {
    ADMIN(1), STUDENT(2), TEACHER(3);

    private final int value;

    AuthEnum(int value) {
        this.value = value;
    }

    public static AuthEnum getEnumByValue(int value) {
        for (AuthEnum anEnum : AuthEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
