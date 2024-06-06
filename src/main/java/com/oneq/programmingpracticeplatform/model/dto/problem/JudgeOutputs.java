package com.oneq.programmingpracticeplatform.model.dto.problem;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JudgeOutputs {
    private long execTime;//ms
    private int execMemory;//KB
    private String output;
}
