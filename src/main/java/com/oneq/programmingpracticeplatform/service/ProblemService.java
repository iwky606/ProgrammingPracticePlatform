package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.dto.SubmissionReq;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;

import javax.servlet.http.HttpServletRequest;

public interface ProblemService {
    long createProblem(HttpServletRequest request);

    long updateProblem(EditProblemRequest editProblemRequest);

    Problem getProblemDetail(Long id, User user);

    void submitCode(SubmissionReq submissionReq, User user);

}
