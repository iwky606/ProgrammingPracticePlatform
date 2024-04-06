package com.oneq.programmingpracticeplatform.service;

import cn.hutool.http.server.HttpServerRequest;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    boolean userRegister(String username, String password, String checkPassword, int auth);

    UserVo login(String username, String userPassword, HttpServletRequest request);
}
