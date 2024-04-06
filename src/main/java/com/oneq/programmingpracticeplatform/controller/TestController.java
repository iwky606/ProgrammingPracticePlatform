package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.model.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static com.oneq.programmingpracticeplatform.constant.UserConstant.USER_LOGIN_STATE;

@Controller
public class TestController {
    @GetMapping("/test")
    public UserVo Hello(HttpServletRequest request) {
        return (UserVo) request.getSession().getAttribute(USER_LOGIN_STATE);
    }
}
