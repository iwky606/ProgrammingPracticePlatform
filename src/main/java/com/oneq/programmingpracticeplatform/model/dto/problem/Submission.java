package com.oneq.programmingpracticeplatform.model.dto.problem;

import com.oneq.programmingpracticeplatform.model.enums.Language;
import lombok.Data;

@Data
public class Submission {
    private String code;
    private long problemId;
    private long problemSetsId;
    private Language lang;
}
