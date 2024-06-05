package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Problem implements Serializable {
    private long id;
    private long creator;
    private String title;
    private long createTime;
    private long updateTime;
    private ProblemDescription description;
    private List<String> tags;
    private ProblemSolution solution;
    private JudgeConfig judgeConfig;
    private static final long serialVersionUID = 1L;
}