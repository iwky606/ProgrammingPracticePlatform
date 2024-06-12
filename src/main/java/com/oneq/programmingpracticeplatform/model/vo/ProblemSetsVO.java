package com.oneq.programmingpracticeplatform.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oneq.programmingpracticeplatform.model.entity.problemsets.SetsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSetsVO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProblemListVo problemList;

    private long id;
    private long createUser;
    private long createTime;
    private long updateTime;
    private Long openTime;
    private Long endTime;
    private SetsInfo info;
}
