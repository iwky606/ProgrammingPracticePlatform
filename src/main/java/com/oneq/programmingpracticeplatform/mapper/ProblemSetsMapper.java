package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.problemsets.ProblemSets;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemSetsMapper {
    void createProblemSets(long id, long createUser, long createTime, long updateTime);

    void editProblemSetsInfo(ProblemSets problemSets);

    Long getCreator(long problemSetsId);

    ProblemSets getProblemSetsDetail(long id);

    List<Long> getSetsCreatorByProblemAndTime(long problemId, long now);
}
