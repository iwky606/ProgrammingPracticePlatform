package com.oneq.programmingpracticeplatform.model.dto.problem;

import lombok.Data;

@Data
public class EditProblemRequest {
    private long id;
    private String description;
    private String tags;
    private String solution;
    private String judgeConfig;
    private String judgeInputs;
    private String judgeOutputs;
}
