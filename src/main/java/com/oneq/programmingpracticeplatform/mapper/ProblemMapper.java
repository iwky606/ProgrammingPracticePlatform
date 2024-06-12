package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemMapper {

    int createProblem(long id, long creator, long createTime, long updateTime);

    int updateProblem(Problem problem);

    Problem getProblemDetail(long id);

    Problem getProblemInputDetail(long id);

    Problem getProblemJudgeInfo(long submissionId);

    int hasProblem(long problemId);

    Problem getProblemWithoutAuth(Long id, long now);

    void updateProblemDurationTime(Problem problem,long problemSetsId);
}
