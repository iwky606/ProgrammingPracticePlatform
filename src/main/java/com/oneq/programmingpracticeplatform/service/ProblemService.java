package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;

import javax.servlet.http.HttpServletRequest;

public interface ProblemService {
    long createProblem(HttpServletRequest request);

    long updateProblem(EditProblemRequest editProblemRequest);
}
