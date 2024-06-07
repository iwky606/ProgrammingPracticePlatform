package com.oneq.programmingpracticeplatform.model.dto;

import com.oneq.programmingpracticeplatform.model.enums.Language;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubmissionReq implements Serializable {
    private String code;
    private long problemId;
    private long problemSetsId;
    private Language lang;
    private static final long serialVersionUID = 1L;

}
