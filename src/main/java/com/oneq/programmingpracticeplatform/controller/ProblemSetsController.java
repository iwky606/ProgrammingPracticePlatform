package com.oneq.programmingpracticeplatform.controller;

import cn.hutool.core.bean.BeanUtil;
import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsInfoRequest;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.ProblemSetsProblemVo;
import com.oneq.programmingpracticeplatform.model.vo.ProblemVo;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public BaseResponse editProblemSetsInfo(EditSetsInfoRequest editSetsInfo, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        problemService.editProblemSets(editSetsInfo,loginUser);
        return ResultUtils.success(null);
    }

    @GetMapping("/problems")
    public BaseResponse<ProblemSetsProblemVo> getProblems(@RequestParam long id, int pageSize, int pageNum, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        int total = problemService.getProblemsSetsTotal(id);
        List<Problem> problems = problemService.getProblems(loginUser, id, pageSize, pageNum);
        List<ProblemVo> problemVos = BeanUtil.copyToList(problems, ProblemVo.class);
        ProblemSetsProblemVo resp = new ProblemSetsProblemVo(total, problemVos);
        return ResultUtils.success(resp);
    }

    @PostMapping("/edit/problem")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse editProblemsSetsProblem(HttpServletRequest req) {

        return ResultUtils.success(null);
    }

}
