package com.oneq.programmingpracticeplatform.typehandler;

import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@MappedTypes({List.class})
public class IntegerToJsonTypeHandler extends ListToJsonTypeHandler<Integer> {
    public IntegerToJsonTypeHandler() {
        super(Integer.class);
    }
}
