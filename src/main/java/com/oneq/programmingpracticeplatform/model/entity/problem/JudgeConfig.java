package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

import java.io.Serializable;

@Data
public class JudgeConfig implements Serializable {
    private Long timeLimit;
    private Long memoryLimit;
    private static final long serialVersionUID = 1L;
}
