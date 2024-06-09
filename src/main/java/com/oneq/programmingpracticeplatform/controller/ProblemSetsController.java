package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/problem_sets")
public class ProblemSetsController {
    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @PostMapping("/create")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse<Long> createProblemSets(HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        long id = problemService.createProblemSets(loginUser);
        return ResultUtils.success(id);
    }

    @PostMapping("/edit/info")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse editProblemSetsInfo(HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);

        return ResultUtils.success(null);
    }

    @PostMapping("/edit/problem")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse editProblemsSetsProblem(HttpServletRequest req) {

        return ResultUtils.success(null);
    }

}
