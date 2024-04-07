package com.oneq.programmingpracticeplatform.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper {

    int createProblem(long id,String creator,long createTime,long updateTime);
}
