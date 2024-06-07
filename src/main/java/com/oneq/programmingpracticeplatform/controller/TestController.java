package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.service.ProblemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
public class TestController {

    @Resource
    private ProblemService problemService;

    @GetMapping("/test")
    public void Hello(HttpServletRequest request) {
    }
}
