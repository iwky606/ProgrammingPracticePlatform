package com.oneq.programmingpracticeplatform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.mapper.ProblemMapper;
import com.oneq.programmingpracticeplatform.mapper.SubmissionMapper;
import com.oneq.programmingpracticeplatform.model.dto.SubmissionReq;
import com.oneq.programmingpracticeplatform.model.dto.problem.EditProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class ProblemServiceImpl implements ProblemService {
    @Resource
    ProblemMapper problemMapper;
    @Resource
    UserService userService;
    @Resource
    Snowflake snowflake;
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    SubmissionMapper submissionMapper;

    @Override
    public long createProblem(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
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
        int updateNums = problemMapper.updateProblem(editProblemRequest.getId(), timestamp, editProblemRequest.getDescription(), editProblemRequest.getTags(), editProblemRequest.getSolution(), editProblemRequest.getJudgeConfig(), editProblemRequest.getVisible(), editProblemRequest.getJudgeInputs(), editProblemRequest.getJudgeOutputs());
        if (updateNums == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改失败");
        }
        return editProblemRequest.getId();
    }

    @Override
    public Problem getProblemDetail(Long id, User user) {
        Problem problemDetail = problemMapper.getProblemDetail(id);
        if (problemDetail == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 如果是题目拥有者或者是管理员是可以直接查看的
        if (user != null && (user.getAuth().equals(AuthEnum.ADMIN) || user.getId() == problemDetail.getCreator())) {
            return problemDetail;
        }

        // 否则判断权限情况
        if (problemDetail.getVisible() == null || problemDetail.getVisible().equals(ProblemVisibleEnum.PRIVATE)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "题目未公开");
        }

        // TODO: 题目处于竞赛中的情况
        return problemDetail;
    }

    @Override
    public void submitCode(SubmissionReq submissionReq, User user) {
        long now = System.currentTimeMillis();

        if (user == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请登录");
        }
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionReq, submission);
        long id = snowflake.nextId();
        submission.setId(id);
        submission.setUserId(user.getId());
        submission.setSubmissionTime(now);
        submissionMapper.createSubmission(submission);

        log.info(submission.toString());
    }

}
