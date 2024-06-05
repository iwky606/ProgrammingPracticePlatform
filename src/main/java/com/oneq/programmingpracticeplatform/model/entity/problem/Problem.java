package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

import java.util.List;

@Data
public class Problem {
    private long id;
    private long creator;
    private String title;
    private long createTime;
    private long updateTime;
    private ProblemDescription description;
    private List<String> tags;
    private ProblemSolution solution;
}