package com.oneq.programmingpracticeplatform.model.entity.problemsets;

import lombok.Data;

@Data
public class ProblemSets {
    private long id;
    private long createUser;
    private long createTime;
    private long updateTime;
    private Long openTime;
    private Long endTime;
    private SetsInfo info;
}
