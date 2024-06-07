package com.oneq.programmingpracticeplatform.model.entity.problem;

import lombok.Data;

import java.io.Serializable;

@Data
public class JudgeConfig implements Serializable {
    private long timeLimit;// 毫秒
    private int memoryLimit;// kb
    private static final long serialVersionUID = 1L;
}
