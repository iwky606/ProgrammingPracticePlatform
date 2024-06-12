package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.dto.SubmissionReq;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.dto.problem.JudgeResult;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsInfoRequest;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.problemsets.ProblemSets;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
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

    long submitCode(SubmissionReq submissionReq, User user);

    void judgeSubmitResult(JudgeResult judgeResult);

    List<Submission> submissionList(Long problemId, Long problemSetId, Long userId, JudgeStatus status, Language lang);

    Submission getSubmissionDetail(long submissionId, User user);

    long createProblemSets(User user);

    void editProblemSets(EditSetsInfoRequest problemSets, User user);

    List<Problem> getProblems(User user, long problemSetsId, int pageSize, int pageNum);

    Integer getProblemsSetsTotal(long problemSetsId);

    List<Problem> getAllProblems();

    void setsAddProblem(EditSetsProblemRequest edit, User req);

    void setsDelProblem(EditSetsProblemRequest edit, User req);

    ProblemSets getProblemSetsDetail(long id);
}
