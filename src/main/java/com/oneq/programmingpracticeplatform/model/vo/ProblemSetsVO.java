package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.entity.problemsets.SetsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSetsVO {
    private ProblemListVo problemList;
    private long id;
    private long createUser;
    private long createTime;
    private long updateTime;
    private Long openTime;
    private Long endTime;
    private SetsInfo info;
}
