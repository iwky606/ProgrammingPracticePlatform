package com.oneq.programmingpracticeplatform.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper {

    int createProblem(long id, long creator, long createTime, long updateTime);

    int updateProblem(long id, long updateTime, String description, String tags, String solution, String judgeConfig, String judgeInputs, String judgeOutputs);
}
