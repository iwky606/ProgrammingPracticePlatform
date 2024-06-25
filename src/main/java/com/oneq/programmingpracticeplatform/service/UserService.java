package com.oneq.programmingpracticeplatform.service;

import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    void userRegister(String username, String password, String checkPassword, AuthEnum auth);

    UserVo login(String username, String userPassword, HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    void updateUserAuth(String username, AuthEnum auth);

    void logout(HttpServletRequest request);
}
