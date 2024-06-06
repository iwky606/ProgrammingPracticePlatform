package com.oneq.programmingpracticeplatform.model.entity.submission;

import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Submission {
    private long id;
    private long problemId;
    private long userId;
    private String code;
    private Language lang;
    private long submissionTime;
    private JudgeStatus status;
    private int execTime;
    private int execMemory;
    private long ProblemSetsId;
}

