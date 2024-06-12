package com.oneq.programmingpracticeplatform.controller;

import cn.hutool.core.bean.BeanUtil;
import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsInfoRequest;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.problemsets.ProblemSets;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.ProblemListVo;
import com.oneq.programmingpracticeplatform.model.vo.ProblemSetsVO;
import com.oneq.programmingpracticeplatform.model.vo.ProblemVo;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/problem_sets")
@Slf4j
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
        problemService.editProblemSets(editSetsInfo, loginUser);
        return ResultUtils.success(null);
    }

    @GetMapping("/detail")
    public BaseResponse<ProblemSetsVO> getSetsDetail(@RequestParam long id, int pageSize, int pageNum, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        // 获取题目集的题目总数
        int total = problemService.getProblemsSetsTotal(id);

        // 获取题目id列表
        List<Problem> problems = problemService.getSetsProblems(loginUser, id, pageSize, pageNum);
        List<ProblemVo> problemVos = BeanUtil.copyToList(problems, ProblemVo.class);
        ProblemListVo problemList = new ProblemListVo(total, problemVos);

        // 获取题目集详情
        ProblemSetsVO resp = new ProblemSetsVO();
        ProblemSets problemSets = problemService.getProblemSetsDetail(id);
        long now = System.currentTimeMillis();
        if (problemSets.getOpenTime() > now && loginUser.getAuth().equals(AuthEnum.STUDENT)) {
            resp.setInfo(problemSets.getInfo());
            resp.setOpenTime(problemSets.getOpenTime());
            resp.setEndTime(problemSets.getEndTime());
        } else {
            BeanUtil.copyProperties(problemSets, resp);
            resp.setProblemList(problemList);
        }
        return ResultUtils.success(resp);
    }

    @PostMapping("/add/problem")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse addProblemsSetsProblem(@RequestBody EditSetsProblemRequest edit, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        problemService.setsAddProblem(edit, loginUser);
        return ResultUtils.success(null);
    }

    @PostMapping("/del/problem")
    @AuthCheck(mustRole = AuthEnum.TEACHER)
    public BaseResponse delProblemSetsProblem(@RequestBody EditSetsProblemRequest edit, HttpServletRequest req) {
        User loginUser = userService.getLoginUser(req);
        problemService.setsDelProblem(edit, loginUser);
        return ResultUtils.success(null);
    }

}
