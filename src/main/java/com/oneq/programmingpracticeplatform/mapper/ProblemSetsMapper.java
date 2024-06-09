package com.oneq.programmingpracticeplatform.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemSetsMapper {
    void createProblemSets(long id, long createUser, long updateTime);
}
