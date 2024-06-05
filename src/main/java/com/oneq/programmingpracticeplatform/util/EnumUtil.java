package com.oneq.programmingpracticeplatform.util;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;

public class EnumUtil {

    public static <T extends BaseEnum> T getEnumByValue(Class<T> myclass, int value) {
        for (T myEnum : myclass.getEnumConstants())
            if (value == myEnum.getValue()) return myEnum;
        return null;
    }

    public static <T extends BaseEnum> T getEnumByLable(Class<T> myclass, int value) {
        for (T myEnum : myclass.getEnumConstants())
            if (myEnum.getValue() == value) return myEnum;
        return null;
    }

}
