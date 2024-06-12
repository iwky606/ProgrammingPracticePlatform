package com.oneq.programmingpracticeplatform.controller;

import cn.hutool.core.bean.BeanUtil;
import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.annotation.LoginRequired;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.dto.SubmissionReq;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import com.oneq.programmingpracticeplatform.model.vo.ProblemListVo;
import com.oneq.programmingpracticeplatform.model.vo.ProblemVo;
import com.oneq.programmingpracticeplatform.model.vo.SubmissionVo;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public BaseResponse<ProblemVo> getProblem(@RequestParam long id, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        Problem problemDetail = problemService.getProblemDetail(id, loginUser);
        return ResultUtils.success(ProblemVo.objToVo(problemDetail));
    }

    @GetMapping("/problems")
    public BaseResponse<List<ProblemVo>> getProblems(Long problemSetsId) {
        log.info(problemSetsId + "");
        return null;
    }

    @PostMapping("/submit")
    public BaseResponse<Long> submitCode(@RequestBody SubmissionReq submission, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        long submissionId = problemService.submitCode(submission, loginUser);
        return ResultUtils.success(submissionId);
    }

    // @formatter:off
    @GetMapping("/submissions")
    public BaseResponse<List<SubmissionVo>> getSubmissionList(
            @RequestParam(value = "problemId", required = false) Long problemId,
            @RequestParam(value = "problemSetId", required = false) Long problemSetId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "status", required = false) JudgeStatus status,
            @RequestParam(value = "lang", required = false) Language lang) {
        List<Submission> submissions = problemService.submissionList(problemId, problemSetId, userId, status, lang);
        List<SubmissionVo> submissionVos = BeanUtil.copyToList(submissions, SubmissionVo.class);

        return ResultUtils.success(submissionVos);
    }
    // @formatter:on

    @GetMapping("/submission/detail")
    @LoginRequired
    public BaseResponse<SubmissionVo> getSubmissionDetail(@RequestParam long id, HttpServletRequest req) {
        User user = userService.getLoginUser(req);
        Submission submission = problemService.getSubmissionDetail(id, user);
        SubmissionVo resp = new SubmissionVo();
        BeanUtil.copyProperties(submission, resp);
        return ResultUtils.success(resp);
    }

    @GetMapping("/list")
    public BaseResponse<ProblemListVo> getProblemList() {
        problemService.getAllProblems();
        return null;
    }
}
