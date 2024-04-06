package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.model.dto.user.UserRegisterRequest;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/ready")
    public String Hello() {
        return "hello world";
    }

    @PostMapping("/register")
    public BaseResponse<Object> RegisterUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String userPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        short auth = userRegisterRequest.getAuth();
        if (StringUtils.isAnyBlank(username, userPassword, checkPassword)) {
            return null;
        }
        userService.userRegister(username, userPassword, checkPassword, auth);
        return ResultUtils.success(null);
    }
}
