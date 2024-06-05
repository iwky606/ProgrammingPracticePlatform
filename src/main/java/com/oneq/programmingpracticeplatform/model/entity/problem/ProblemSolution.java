package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProblemSolution implements Serializable {
    private String content;
    private static final long serialVersionUID = 1L;
}
