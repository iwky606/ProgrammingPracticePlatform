package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.dto.problem.Submission;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.ProblemVo;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/problem")
@Slf4j
public class ProblemController {
    @Resource
    ProblemService problemService;

    @Resource
    UserService userService;

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

    @GetMapping("/detail")
    public BaseResponse<ProblemVo> getProblem(@RequestParam Long id, HttpServletRequest req) {
        log.info(String.valueOf(id));
        User loginUser = userService.getLoginUser(req);

        Problem problemDetail = problemService.getProblemDetail(id, loginUser);
        return ResultUtils.success(ProblemVo.objToVo(problemDetail));
    }

    @PostMapping("/submit")
    public BaseResponse submitSolution(@RequestBody Submission submission) {

        return ResultUtils.success(null);
    }
}
