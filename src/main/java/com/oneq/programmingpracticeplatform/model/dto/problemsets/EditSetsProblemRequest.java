package com.oneq.programmingpracticeplatform.model.dto.problemsets;

import lombok.Data;

import java.io.Serializable;

@Data
public class EditSetsProblemRequest implements Serializable {
    private long problemSetsId;
    private long problemId;
    private static final long serialVersionUID = 1;
}
