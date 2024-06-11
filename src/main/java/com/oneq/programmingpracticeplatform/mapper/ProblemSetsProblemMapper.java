package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemSetsProblemMapper {
    int getProblemsTotal(long problemSetsId);

    List<Problem> getProblems(long problemSetsId, int limit, int offSet);
}
