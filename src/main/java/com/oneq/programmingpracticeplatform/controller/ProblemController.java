package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @PostMapping
    public BaseResponse<Long> createProblem() {
        Long id = null;
        return ResultUtils.success(id);
    }
}
