package com.oneq.programmingpracticeplatform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import com.oneq.programmingpracticeplatform.annotation.Cache;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.mapper.ProblemMapper;
import com.oneq.programmingpracticeplatform.mapper.ProblemSetsMapper;
import com.oneq.programmingpracticeplatform.mapper.ProblemSetsProblemMapper;
import com.oneq.programmingpracticeplatform.mapper.SubmissionMapper;
import com.oneq.programmingpracticeplatform.model.dto.SubmissionReq;
import com.oneq.programmingpracticeplatform.model.dto.problem.*;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsInfoRequest;
import com.oneq.programmingpracticeplatform.model.dto.problemsets.EditSetsProblemRequest;
import com.oneq.programmingpracticeplatform.model.entity.problemsets.ProblemSets;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.entity.problem.JudgeConfig;
import com.oneq.programmingpracticeplatform.model.entity.problem.Problem;
import com.oneq.programmingpracticeplatform.model.entity.submission.Submission;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.enums.JudgeStatus;
import com.oneq.programmingpracticeplatform.model.enums.Language;
import com.oneq.programmingpracticeplatform.service.FileService;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProblemServiceImpl implements ProblemService {
    @Resource
    ProblemMapper problemMapper;
    @Resource
    ProblemSetsMapper problemSetsMapper;
    @Resource
    ProblemSetsProblemMapper problemSetsProblemMapper;
    @Resource
    UserService userService;
    @Resource
    FileService fileService;
    @Resource
    Snowflake snowflake;
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    SubmissionMapper submissionMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
        Problem problem = new Problem();
        BeanUtil.copyProperties(editProblemRequest, problem);
        problem.setUpdateTime(timestamp);

        int updateNums = problemMapper.updateProblem(problem);
        if (updateNums == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改失败");
        }

        // 修改题目信息，就将题目缓存设置为过期
        redisTemplate.expire(problemCacheKey(editProblemRequest.getId()), 0, TimeUnit.SECONDS);
        return editProblemRequest.getId();
    }

    @Override
    public Problem getProblemDetail(Long id, User user) {
        // 如果是管理员、教师是可以直接查看的
        if (user != null && (user.getAuth().equals(AuthEnum.ADMIN) || user.getAuth().equals(AuthEnum.TEACHER))) {

            return getProblemDetailWithCache(id);
        }

        long now = System.currentTimeMillis();
        Problem problem = problemMapper.getProblemWithoutAuth(id, now);
        if (problem.getEndTime() != 0 && problem.getEndTime() > now) {
            problem.setTags(null);
        }
        return problem;
    }

    @Cache(key = "problem.detail", expire = 15, timeUnit = TimeUnit.MINUTES)
    public Problem getProblemDetailWithCache(long id) {
        // 一定非法的id
        if (id <= 1000000000000000000L) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        return problemMapper.getProblemDetail(id);
    }

    private String problemCacheKey(long id) {
        return "problem.detail." + id;
    }

    @Override
    public long submitCode(SubmissionReq submissionReq, User user) {
        long now = System.currentTimeMillis();

        if (user == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请登录");
        }
        Submission submission = new Submission();
        BeanUtil.copyProperties(submissionReq, submission);
        long submissionId = snowflake.nextId();
        submission.setId(submissionId);
        submission.setUserId(user.getId());
        submission.setSubmissionTime(now);

        submissionMapper.createSubmission(submission);

        Problem problemDetail = getProblemDetailWithCache(submission.getProblemId());
        if (problemDetail == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交的题目不存在");
        }
        String[] filesByIds = fileService.getIOFiles(problemDetail.getJudgeInputs()).toArray(new String[problemDetail.getJudgeInputs().size()]);

        ResourceLimit resourceLimit = new ResourceLimit();
        BeanUtil.copyProperties(problemDetail.getJudgeConfig(), resourceLimit);
        JudgeTask judgeTask = new JudgeTask(submission.getId(), filesByIds, submission.getCode(), submission.getLang(), resourceLimit, null);
        rabbitTemplate.convertAndSend("judge.queue", judgeTask);
        return submissionId;
    }

    @Override
    public void judgeSubmitResult(JudgeResult judgeResult) {
        Problem problemDetail = problemMapper.getProblemJudgeInfo(judgeResult.getJudgeId());
        List<String> outputs = fileService.getIOFiles(problemDetail.getJudgeOutputs());

        Submission submission = new Submission();
        submission.setId(judgeResult.getJudgeId());
        submission.setStatus(JudgeStatus.ACCEPT);
        submission.setExecTime(0);
        submission.setExecMemory(0);
        if (!judgeResult.getJudgeStatus().equals(JudgeStatus.NORMAL)) {
            // 说明程序未完全执行
            submission.setStatus(judgeResult.getJudgeStatus());
        } else if (outputs.size() != judgeResult.getOutputs().length) {
            // 题目设置有误, input和output不匹配
            submission.setStatus(JudgeStatus.WRONG_ANSWER);
        } else {
            // 答案比较，以及运行时间判断
            JudgeConfig judgeConfig = problemDetail.getJudgeConfig();
            for (int i = 0; i < outputs.size(); i++) {
                JudgeOutputs output = judgeResult.getOutputs()[i];

                // 取最大值保存
                long time = Math.max(output.getExecTime(), submission.getExecTime());
                submission.setExecTime((int) time);

                long memory = Math.max(output.getExecMemory(), submission.getExecMemory());
                submission.setExecMemory((int) memory);

                // 比较答案
                if (!outputs.get(i).trim().equals(output.getOutput().trim())) {
                    submission.setStatus(JudgeStatus.WRONG_ANSWER);
                    break;
                }
                // 判断时间是否超限
                if (judgeConfig.getTimeLimit() < output.getExecTime()) {
                    submission.setStatus(JudgeStatus.TIME_LIMIT_EXCEED);
                    break;
                }
                // 判断内存超限
                if (judgeConfig.getMemoryLimit() < output.getExecMemory()) {
                    submission.setStatus(JudgeStatus.MEMORY_LIMIT_EXCEED);
                    break;
                }
            }
        }

        updateSubmission(submission);
    }

    @Override
    public List<Submission> submissionList(Long problemId, Long problemSetId, Long userId, JudgeStatus status, Language lang) {
        List<Submission> submission = submissionMapper.getSubmissions(problemId, problemSetId, userId, status, lang);
        return submission;
    }

    @Override
    public Submission getSubmissionDetail(long submissionId, User user) {
        Submission submission = submissionMapper.getSubmission(submissionId);

        // 可以优先通过的情况
        if (user.getAuth().equals(AuthEnum.ADMIN)) {
            return submission;
        }
        if (submission.getProblemSetsId() > 0 && user.getAuth().equals(AuthEnum.TEACHER)) {
            ProblemSets problemSets = problemSetsMapper.getProblemSetsBySubmission(submissionId);
            if (problemSets.getCreateUser() == user.getId()) {
                return submission;
            }
        }

        // 不通过的情况
        if (user.getId() != submission.getUserId()) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "无权查看该代码");
        }

        return submission;
    }

    @Override
    public long createProblemSets(User user) {
        long now = System.currentTimeMillis();
        long id = snowflake.nextId();
        problemSetsMapper.createProblemSets(id, user.getId(), now, now);
        return id;
    }

    @Override
    @Transactional
    public void editProblemSets(EditSetsInfoRequest req, User user) {
        Long creator = getProblemSetsCreator(req.getId());
        if (!user.getAuth().equals(AuthEnum.ADMIN)) {
            if (user.getId() != creator) {
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "没有权限修改该题目");
            }
        }
        ProblemSets problemSets = new ProblemSets();
        BeanUtil.copyProperties(req, problemSets);
        long now = System.currentTimeMillis();
        problemSets.setUpdateTime(now);
        problemSetsMapper.editProblemSetsInfo(problemSets);

        if (req.getOpenTime() != null || req.getEndTime() != null) {
            Problem problem = new Problem();
            // problem.setId();
        }
    }

    @Cache(key = "sets.creator", expire = 5, timeUnit = TimeUnit.MINUTES)
    public Long getProblemSetsCreator(long problemSetsId) {
        Long creator = problemSetsMapper.getCreator(problemSetsId);
        return creator;
    }

    @Override
    public List<Problem> getSetsProblems(User user, long problemSetsId, int pageSize, int pageNum) {
        // TODO: open_time check
        // TODO: cahce
        int offSet = (pageNum - 1) * pageSize;
        List<Problem> problems = problemSetsProblemMapper.getProblems(problemSetsId, pageSize, offSet);
        return problems;
    }

    @Cache(key = "sets.total", expire = 3, timeUnit = TimeUnit.HOURS)
    public Integer getProblemsSetsTotal(long problemSetsId) {
        int total = problemSetsProblemMapper.getProblemsTotal(problemSetsId);
        return total;
    }

    @Override
    public List<Problem> getAllProblems() {
        // TODO:
        return null;
    }

    @Override
    @Transactional // 添加事务保证数据一致性
    public void setsAddProblem(EditSetsProblemRequest edit, User req) {
        int count = problemMapper.hasProblem(edit.getProblemId());
        if (count == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "无效的题目id");
        }

        ProblemSets problemSetsDetail = problemSetsMapper.getProblemSetsDetail(edit.getProblemSetsId());
        if (problemSetsDetail == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "无效的题目集id");
        }
        try {
            problemSetsProblemMapper.addProblem(edit.getProblemSetsId(), edit.getProblemId());
            Problem problem = new Problem();
            problem.setId(edit.getProblemId());
            problem.setOpenTime(problemSetsDetail.getOpenTime());
            problem.setEndTime(problemSetsDetail.getEndTime());
            problemMapper.updateProblem(problem);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "这个题目已经被其他题目占用或者您已添加这个题目到该题目集");
        }
        expireSetsTotalCache(edit.getProblemSetsId());
    }

    public void expireSetsTotalCache(long id) {
        redisTemplate.expire("sets.total." + id, 0, TimeUnit.SECONDS);
    }

    @Override
    public void setsDelProblem(EditSetsProblemRequest edit, User req) {
        problemSetsProblemMapper.delProblem(edit.getProblemSetsId(), edit.getProblemId());
        expireSetsTotalCache(edit.getProblemSetsId());
    }

    @Override
    public ProblemSets getProblemSetsDetail(long id) {
        return problemSetsMapper.getProblemSetsDetail(id);
    }

    @Override
    public List<ProblemSets> getAllProblemSets(int pageSize, int pageNum) {
        int offSet = (pageNum - 1) * pageSize;
        return problemSetsMapper.getAllProblemSetsList(offSet, pageSize);
    }


    public void updateSubmission(Submission submission) {
        submissionMapper.updateSubmission(submission);
    }

}
