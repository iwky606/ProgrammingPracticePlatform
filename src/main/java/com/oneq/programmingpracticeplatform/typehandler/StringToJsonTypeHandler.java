package com.oneq.programmingpracticeplatform.typehandler;

import org.apache.ibatis.type.MappedTypes;

import java.util.List;

@MappedTypes({List.class})
public class StringToJsonTypeHandler extends ListToJsonTypeHandler<String> {
    public StringToJsonTypeHandler() {
        super(String.class);
    }
}
