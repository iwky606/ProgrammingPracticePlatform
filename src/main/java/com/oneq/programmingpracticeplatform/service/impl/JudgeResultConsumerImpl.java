package com.oneq.programmingpracticeplatform.service.impl;

import com.oneq.programmingpracticeplatform.model.dto.problem.JudgeResult;
import com.oneq.programmingpracticeplatform.service.JudgeResultConsumer;
import com.oneq.programmingpracticeplatform.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class JudgeResultConsumerImpl implements JudgeResultConsumer {

    @Resource
    ProblemService problemService;

    @Override
    @RabbitListener(queues = "result.judge.queue")
    public void consumeMessage(JudgeResult message) {


        problemService.judgeSubmitResult(message);

    }

}
