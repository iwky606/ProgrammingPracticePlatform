package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;
import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubmissionMapper {
    void createSubmission(Submission submission);

    void updateSubmission(Submission submission);

    List<Submission> getSubmissions(Long problemId, Long problemSetsId, Long userId, JudgeStatus status, Language lang);
}
