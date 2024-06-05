package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemDescription;
import com.oneq.programmingpracticeplatform.model.entity.problem.ProblemSolution;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemMapper {

    int createProblem(long id, long creator, long createTime, long updateTime);

    int updateProblem(long id, long updateTime, ProblemDescription description, List<String> tags, ProblemSolution solution, JudgeConfig judgeConfig, List<Integer> judgeInputs, List<Integer> judgeOutputs);
}
