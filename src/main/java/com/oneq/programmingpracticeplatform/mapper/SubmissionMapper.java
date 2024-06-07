package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubmissionMapper {
    void createSubmission(Submission submission);

    void updateSubmission(Submission submission);
}
