package com.oneq.programmingpracticeplatform.controller;

import com.oneq.programmingpracticeplatform.annotation.AuthCheck;
import com.oneq.programmingpracticeplatform.annotation.LoginRequired;
import com.oneq.programmingpracticeplatform.common.BaseResponse;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.common.ResultUtils;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.model.dto.user.UserLoginRequest;
import com.oneq.programmingpracticeplatform.model.dto.user.UserRegisterRequest;
import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;
import com.oneq.programmingpracticeplatform.service.UserService;
import com.oneq.programmingpracticeplatform.util.EnumUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    UserService userService;

    @Value("${admin_key}")
    private String adminKey;

    @ApiOperation(value = "注册", notes = "")
    @PostMapping("/register")
    public BaseResponse<UserVo> RegisterUser(@RequestBody UserRegisterRequest userRegisterRequest,HttpServletRequest request) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userRegisterRequest.getUsername();
        String userPassword = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (AuthEnum.ADMIN.equals(userRegisterRequest.getAuth())) {
            String inputAdminKey = userRegisterRequest.getAdminKey();
            log.info("admin key:{}, {}", userRegisterRequest.getAdminKey(), adminKey);
            if (StringUtils.isBlank(inputAdminKey) || !adminKey.equals(inputAdminKey)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "admin账户注册非法");
            }
        } else if (AuthEnum.TEACHER.equals(userRegisterRequest.getAuth())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "教师账户由管理员授权，禁止注册");
        }
        if (StringUtils.isAnyBlank(username, userPassword, checkPassword)) {
            return null;
        }
        UserVo userVo=userService.userRegister(username, userPassword, checkPassword, userRegisterRequest.getAuth(),request);

        return ResultUtils.success(userVo);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public BaseResponse<UserVo> Login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserVo userVo = userService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword(), request);
        return ResultUtils.success(userVo);
    }

    @ApiOperation(value = "权限修改（仅仅admin账户可以使用）", notes = "")
    @PostMapping("/grantAuth")
    @AuthCheck(mustRole = AuthEnum.ADMIN)
    public BaseResponse<String> grantAuth(String username, int auth) {
        if (StringUtils.isAnyBlank(username) || EnumUtil.getEnumByValue(AuthEnum.class, auth) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        userService.updateUserAuth(username, EnumUtil.getEnumByValue(AuthEnum.class, auth));

        return ResultUtils.success("修改成功");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    @LoginRequired
    public BaseResponse logout(HttpServletRequest req) {
        userService.logout(req);
        return ResultUtils.success(null);
    }
}
