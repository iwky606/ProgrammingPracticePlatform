package com.oneq.programmingpracticeplatform.model.dto.problem;

import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class JudgeResult implements Serializable {
    private Long judgeId;
    private JudgeOutputs[] outputs;
    private JudgeStatus judgeStatus;
    private String judgeSystemId;
    private static final long serialVersionUID = 1L;
}
