package com.oneq.programmingpracticeplatform.model.entity.problemsets;

import lombok.Data;

@Data
public class ProblemSets {
    private long id;
    private long createUser;
    private long createTime;
    private long updateTime;
    private long openTime;
    private long type;
    private SetsInfo info;
}
