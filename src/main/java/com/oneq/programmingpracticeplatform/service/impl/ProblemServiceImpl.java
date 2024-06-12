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
import com.oneq.programmingpracticeplatform.model.enums.ProblemVisibleEnum;
import com.oneq.programmingpracticeplatform.service.FileService;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
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
        int updateNums = problemMapper.updateProblem(editProblemRequest.getId(), timestamp, editProblemRequest.getTitle(), editProblemRequest.getDescription(), editProblemRequest.getTags(), editProblemRequest.getSolution(), editProblemRequest.getJudgeConfig(), editProblemRequest.getVisible(), editProblemRequest.getJudgeInputs(), editProblemRequest.getJudgeOutputs());
        if (updateNums == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "修改失败");
        }

        // 修改题目信息，就将题目缓存设置为过期
        redisTemplate.expire(problemCacheKey(editProblemRequest.getId()), 0, TimeUnit.SECONDS);
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

    /**
     * cacheTime单位为秒
     */
    public Problem getProblemDetailWithCache(long id, long cacheTime) {
        // 一定非法的id
        if (id <= 1000000000000000000L) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        if (cacheTime > 0) {
            Object res = redisTemplate.opsForValue().get(problemCacheKey(id));
            if (res != null) {
                return (Problem) res;
            }
        }
        Problem problemDetail = problemMapper.getProblemDetail(id);
        if (cacheTime > 0 && problemDetail != null) {
            redisTemplate.opsForValue().set(problemCacheKey(id), problemDetail, cacheTime, TimeUnit.SECONDS);
        }
        return problemDetail;
    }

    private String problemCacheKey(long id) {
        return "problem.detail." + id;
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

        Problem problemDetail = getProblemDetailWithCache(submission.getProblemId(), 60 * 15);
        if (problemDetail == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交的题目不存在");
        }
        String[] filesByIds = fileService.getIOFiles(problemDetail.getJudgeInputs()).toArray(new String[problemDetail.getJudgeInputs().size()]);

        ResourceLimit resourceLimit = new ResourceLimit();
        BeanUtil.copyProperties(problemDetail.getJudgeConfig(), resourceLimit);
        JudgeTask judgeTask = new JudgeTask(submission.getId(), filesByIds, submission.getCode(), submission.getLang(), resourceLimit, null);
        rabbitTemplate.convertAndSend("judge.queue", judgeTask);
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
            // TODO: 题目集的拥有者可以查看代码
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
    public void editProblemSets(EditSetsInfoRequest req, User user) {
        Long creator = getProblemSetsCreator(req.getId());
        if (!user.getAuth().equals(AuthEnum.ADMIN)) {
            if (user.getId() != creator) {
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "没有权限修改该题目");
            }
        }
        ProblemSets problemSets = new ProblemSets();
        BeanUtil.copyProperties(req, problemSets);
        problemSetsMapper.editProblemSetsInfo(problemSets);
    }

    @Cache(key = "sets.creator", expire = 5, timeUnit = TimeUnit.MINUTES)
    public Long getProblemSetsCreator(long problemSetsId) {
        Long creator = problemSetsMapper.getCreator(problemSetsId);
        return creator;
    }

    @Override
    public List<Problem> getProblems(User user, long problemSetsId, int pageSize, int pageNum) {
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
    public void setsAddProblem(EditSetsProblemRequest edit, User req) {
        int count = problemMapper.hasProblem(edit.getProblemId());
        if (count == 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "无效的题目id");
        }
        try {
            problemSetsProblemMapper.addProblem(edit.getProblemSetsId(), edit.getProblemId());
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "已经添加了这个题目");
        }
    }

    @Override
    public void setsDelProblem(EditSetsProblemRequest edit, User req) {
        problemSetsProblemMapper.delProblem(edit.getProblemSetsId(),edit.getProblemId());
    }

    public void updateSubmission(Submission submission) {
        submissionMapper.updateSubmission(submission);
    }

}
