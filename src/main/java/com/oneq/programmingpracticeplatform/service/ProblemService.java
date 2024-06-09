package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.dto.SubmissionReq;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.dto.problem.JudgeResult;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;
import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProblemService {
    long createProblem(HttpServletRequest request);

    long updateProblem(EditProblemRequest editProblemRequest);

    Problem getProblemDetail(Long id, User user);

    void submitCode(SubmissionReq submissionReq, User user);

    void JudgeSubmitResult(JudgeResult judgeResult);

    List<Submission> SubmissionList(Long problemId, Long problemSetId, Long userId, JudgeStatus status, Language lang);

    Submission GetSubmissionDetail(long submissionId,User user);

    long CreateProblemSets(User user);
}
