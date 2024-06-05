package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/problem")
public class ProblemController {
    @Resource
    ProblemService problemService;

    /*
     * 返回problem_id
     * */
    @PostMapping("/create")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse<Long> createProblem(HttpServletRequest request) {
        long id = problemService.createProblem(request);
        return ResultUtils.success(id);
    }

    @PostMapping("/edit")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse<Long> editProblem(@RequestBody EditProblemRequest editProblemRequest) {
        long id = problemService.updateProblem(editProblemRequest);
        return ResultUtils.success(id);
    }

    // @GetMapping("/detail")
    // public BaseResponse<Pro>
}
