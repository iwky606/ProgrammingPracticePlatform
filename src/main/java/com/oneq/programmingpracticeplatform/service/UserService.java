package com.oneq.programmingpracticeplatform.service;

import cn.hutool.http.server.HttpServerRequest;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    boolean userRegister(String username, String password, String checkPassword, int auth);

    UserVo login(String username, String userPassword, HttpServletRequest request);

    UserVo getLoginUser(HttpServletRequest request);

    boolean updateUserAuth(String username, AuthEnum auth);
}
