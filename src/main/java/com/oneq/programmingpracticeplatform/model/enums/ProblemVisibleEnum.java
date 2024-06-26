package com.oneq.programmingpracticeplatform.model.enums;

import com.oneq.programmingpracticeplatform.model.enums.base.BaseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "题目可见性")
public enum ProblemVisibleEnum implements BaseEnum {

    @ApiModelProperty(value = "私有表示不可见") PRIVATE(0), @ApiModelProperty(value = "公开表示都可见") PUBLIC(1), @ApiModelProperty(value = "竞赛表示当题目集开放可见") CONTEST(2);

    private final int value;

    ProblemVisibleEnum(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
