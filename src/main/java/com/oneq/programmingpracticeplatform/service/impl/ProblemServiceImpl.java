package com.oneq.programmingpracticeplatform.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.mapper.ProblemMapper;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Resource
    ProblemMapper problemMapper;

    @Resource
    UserService userService;
    @Resource
    Snowflake snowflake;

    @Override
    public long createProblem(HttpServletRequest request) {
        UserVo loginUser = userService.getLoginUser(request);
        long id = snowflake.nextId();
        long timestamp = System.currentTimeMillis();
        int rowNums = problemMapper.createProblem(id, loginUser.getId(), timestamp, timestamp);
        if (rowNums == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "创建题目失败");
        }
        return id;
    }

    @Override
    public long updateProblem(EditProblemRequest editProblemRequest) {
        long timestamp = System.currentTimeMillis();
        int updateNums = problemMapper.updateProblem(editProblemRequest.getId(), timestamp, editProblemRequest.getDescription(), editProblemRequest.getTags(), editProblemRequest.getSolution(), editProblemRequest.getJudgeConfig(), editProblemRequest.getJudgeInputs(), editProblemRequest.getJudgeOutputs());
        if (updateNums == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改失败");
        }
        return editProblemRequest.getId();
    }

    @Override
    public Problem getProblemDetail(Long id) {
        return problemMapper.getProblemDetail(id);
    }

}
