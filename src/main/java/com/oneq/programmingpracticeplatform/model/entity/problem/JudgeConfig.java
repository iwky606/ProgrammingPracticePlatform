package com.oneq.programmingpracticeplatform.model.entity.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class JudgeConfig implements Serializable {
    @ApiModelProperty(value = "时间限制(ms)")
    private long timeLimit;
    @ApiModelProperty(value = "内存限制(kb)")
    private int memoryLimit;
    private static final long serialVersionUID = 1L;
}
