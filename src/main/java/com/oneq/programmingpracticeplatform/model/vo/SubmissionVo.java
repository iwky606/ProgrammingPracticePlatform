package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubmissionVo implements Serializable {
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

    private static final long serialVersionUID = 1L;
}
