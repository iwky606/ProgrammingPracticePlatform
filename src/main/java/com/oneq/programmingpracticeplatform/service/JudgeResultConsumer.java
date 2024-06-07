package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.dto.problem.JudgeResult;

public interface JudgeResultConsumer {
    void consumeMessage(JudgeResult message);
}
