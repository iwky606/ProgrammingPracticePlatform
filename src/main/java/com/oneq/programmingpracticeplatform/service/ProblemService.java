package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.dto.problem.Submission;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface ProblemService {
    long createProblem(HttpServletRequest request);

    long updateProblem(EditProblemRequest editProblemRequest);

    Problem getProblemDetail(Long id, User user);

    void submitCode(Submission submission);
}
