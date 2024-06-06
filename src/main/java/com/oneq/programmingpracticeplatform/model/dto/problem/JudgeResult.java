package com.oneq.programmingpracticeplatform.model.dto.problem;

import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JudgeResult {
    private Long judgeId;
    private JudgeOutputs[] outputs;
    private JudgeStatus judgeStatus;
}
