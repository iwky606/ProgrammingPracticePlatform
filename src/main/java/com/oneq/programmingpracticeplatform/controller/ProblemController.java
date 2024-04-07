package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Resource
    ProblemService problemService;

    @PostMapping
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse<Long> createProblem(HttpServletRequest request) {
        long id = problemService.createProblem(request);
        return ResultUtils.success(id);
    }
}
