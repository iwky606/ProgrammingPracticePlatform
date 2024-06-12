package com.oneq.programmingpracticeplatform.model.entity.problem;

import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class Problem implements Serializable {
    private Long id;
    private Long creator;
    private String title;
    private Long createTime;
    private Long updateTime;
    private ProblemDescription description;
    private List<String> tags;
    private ProblemSolution solution;
    private JudgeConfig judgeConfig;
    private ProblemVisibleEnum visible;
    private Long openTime;
    private Long endTime;
    private List<Integer> judgeInputs;
    private List<Integer> judgeOutputs;
    private static final long serialVersionUID = 1L;
}
