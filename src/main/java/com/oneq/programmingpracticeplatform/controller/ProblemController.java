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
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "创建题目，返回题目id", notes = "")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse<Long> createProblem(@RequestBody(required = false) EditProblemRequest params, HttpServletRequest request) {
        long id = problemService.createProblem(params,request);
        return ResultUtils.success(id);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑题目", notes = "")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse<Long> editProblem(@RequestBody EditProblemRequest editProblemRequest) {
        long id = problemService.updateProblem(editProblemRequest);
        return ResultUtils.success(id);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "获取题目详情", notes = "教师，admin和学生看到的详情不一样")
    public BaseResponse<ProblemVo> getProblem(@RequestParam long id, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        Problem problemDetail = problemService.getProblemDetail(id, loginUser);
        return ResultUtils.success(ProblemVo.objToVo(problemDetail));
    }

    @GetMapping("/problems")
    @ApiOperation(value = "获取所有题目")
    public BaseResponse<List<ProblemVo>> getProblems(@RequestParam int pageNum, @RequestParam int pageSize, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        List<Problem> allProblems = problemService.getAllProblems(pageNum, pageSize, loginUser);
        List<ProblemVo> problemVos = BeanUtil.copyToList(allProblems, ProblemVo.class);
        return ResultUtils.success(problemVos);
    }

    @PostMapping("/submit")
    @ApiOperation(value = "提交代码接口, 返回submission_id")
    public BaseResponse<Long> submitCode(@RequestBody SubmissionReq submission, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        long submissionId = problemService.submitCode(submission, loginUser);
        return ResultUtils.success(submissionId);
    }

    // @formatter:off
    @GetMapping("/submissions")
    @ApiOperation(value = "提交列表")
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
    @ApiOperation(value = "提交详情")
    @LoginRequired
    public BaseResponse<SubmissionVo> getSubmissionDetail(@RequestParam long id, HttpServletRequest req) {
        User user = userService.getLoginUser(req);
        Submission submission = problemService.getSubmissionDetail(id, user);
        SubmissionVo resp = new SubmissionVo();
        BeanUtil.copyProperties(submission, resp);
        return ResultUtils.success(resp);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除题目")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse delProblem(@RequestParam long id) {
        problemService.delProblem(id);
        return ResultUtils.success(null);
    }

    // @PostMapping("/create/detail")
    // @ApiOperation(value = "创建题目携带默认参数")
    // @AuthCheck(mustRole = AuthEnum.TEACHER)
    // public BaseResponse<Long> createProblemWithParams(@RequestBody EditProblemRequest) {
    //
    //     return ResultUtils.success(1L);
    // }
}
