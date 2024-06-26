package com.oneq.programmingpracticeplatform.model.dto.problem;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "编辑题目请求参数")
public class EditProblemRequest implements Serializable {
    @ApiModelProperty(value = "题目id", required = true)
    private long id;
    @ApiModelProperty(value = "题目名称")
    private String title;
    private ProblemDescription description;
    private List<String> tags;
    private ProblemSolution solution;
    private JudgeConfig judgeConfig;
    @ApiModelProperty(value = "题目输入的测试用例的文件id，用数组是因为有多个测试用例")
    private List<Integer> judgeInputs;
    private List<Integer> judgeOutputs;
    private ProblemVisibleEnum visible;
    private static final long serialVersionUID = 1L;
}
