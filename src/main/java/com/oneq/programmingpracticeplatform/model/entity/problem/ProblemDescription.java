package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

@Data
public class ProblemDescription {
    private String content;
    private String exampleInput;
    private String exampleOutput;
    private String hint;
}
