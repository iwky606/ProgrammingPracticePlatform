package com.oneq.programmingpracticeplatform.model.dto.problem;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class JudgeOutputs implements Serializable {
    private long execTime;//ms
    private int execMemory;//KB
    private String output;
    private static final long serialVersionUID = 1L;
}
