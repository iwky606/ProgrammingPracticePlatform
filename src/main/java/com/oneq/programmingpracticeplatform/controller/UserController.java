package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.mapper.UserMapper;
import com.oneq.programmingpracticeplatform.model.dto.user.UserLoginRequest;
import com.oneq.programmingpracticeplatform.model.dto.user.UserRegisterRequest;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;
import com.oneq.programmingpracticeplatform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/register")
    public BaseResponse<Object> RegisterUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String userPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        int auth = userRegisterRequest.getAuth();
        if (StringUtils.isAnyBlank(username, userPassword, checkPassword)) {
            return null;
        }
        userService.userRegister(username, userPassword, checkPassword, auth);
        return ResultUtils.success(null);
    }

    @PostMapping("/login")
    public BaseResponse<UserVo> Login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVo userVo = userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword(), request);
        return ResultUtils.success(userVo);
    }

    @PostMapping("/grantAuth")
    @AuthCheck(mustRole = AuthEnum.ADMIN)
    public BaseResponse<String> grantAuth(String username, int auth) {
        if (StringUtils.isAnyBlank(username) || AuthEnum.getEnumByValue(auth) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.updateUserAuth(username, AuthEnum.getEnumByValue(auth));

        return ResultUtils.success("修改成功");
    }
}
