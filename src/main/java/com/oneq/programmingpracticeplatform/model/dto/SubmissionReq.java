package com.oneq.programmingpracticeplatform.model.dto;

import com.oneq.programmingpracticeplatform.model.enums.Language;
import lombok.Data;

@Data
public class SubmissionReq {
    private String code;
    private long problemId;
    private long problemSetsId;
    private Language lang;
}
