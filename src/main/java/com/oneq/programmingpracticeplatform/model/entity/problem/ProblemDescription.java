package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProblemDescription implements Serializable {
    private String content;
    private String exampleInput;
    private String exampleOutput;
    private String hint;
    private static final long serialVersionUID = 1L;
}
