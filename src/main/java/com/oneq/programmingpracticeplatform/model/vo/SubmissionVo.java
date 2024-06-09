package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import lombok.Data;

@Data
public class SubmissionVo {
    private long id;
    private long problemId;
    private long userId;
    private String username;
    private String code;
    private Language lang;
    private long submissionTime;
    private JudgeStatus status;
    private int execTime;
    private int execMemory;
    private long ProblemSetsId;
}
