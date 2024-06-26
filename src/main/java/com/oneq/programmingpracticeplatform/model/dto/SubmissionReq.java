package com.oneq.programmingpracticeplatform.model.dto;

import com.oneq.programmingpracticeplatform.model.enums.Language;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "提交代码参数")
public class SubmissionReq implements Serializable {
    @ApiModelProperty(required = true)
    private String code;
    @ApiModelProperty(value = "题目id", required = true)
    private long problemId;
    @ApiModelProperty(value = "题目集id")
    private long problemSetsId;
    @ApiModelProperty(value = "代码语言，目前只支持java8")
    private Language lang;
    private static final long serialVersionUID = 1L;

}
