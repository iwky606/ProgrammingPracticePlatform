package com.oneq.programmingpracticeplatform.model.entity.problem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "题目的描述详情")
public class ProblemDescription implements Serializable {
    @ApiModelProperty(value = "主要内容")
    private String content;
    @ApiModelProperty(value = "输入样例")
    private String exampleInput;
    @ApiModelProperty(value = "输出样例")
    private String exampleOutput;
    @ApiModelProperty(value = "提示")
    private String hint;
    private static final long serialVersionUID = 1L;
}
