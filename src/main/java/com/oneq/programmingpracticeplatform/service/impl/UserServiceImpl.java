package com.oneq.programmingpracticeplatform.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.oneq.programmingpracticeplatform.common.ErrorCode;
import com.oneq.programmingpracticeplatform.exception.BusinessException;
import com.oneq.programmingpracticeplatform.mapper.UserMapper;
import com.oneq.programmingpracticeplatform.model.entity.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import com.oneq.programmingpracticeplatform.model.vo.UserVo;
import com.oneq.programmingpracticeplatform.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.oneq.programmingpracticeplatform.constant.UserConstant.USER_LOGIN_STATE;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Resource
    Snowflake snowflake;

    private static final String SALT = "oneqxowikdj.";

    @Override
    public boolean userRegister(String username, String password, String checkPassword, int auth) {
        if (auth <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户权限为空");
        }
        // 1. 校验
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (username.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (username.intern()) {
            Integer ok = userMapper.queryUser(username);
            if (ok != null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名重复");
            }
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUsername(username);
            user.setPassword(encryptPassword);
            user.setAuth(AuthEnum.getEnumByValue(auth));
            long timestamp = System.currentTimeMillis();
            user.setCreateTime(timestamp);
            user.setUpdateTime(timestamp);
            user.setId(snowflake.nextId());
            int rows = userMapper.insertUser(user);
            if (rows == 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return true;
        }
    }

    @Override
    public UserVo login(String username, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(username, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码为空");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = userMapper.queryUserPassword(username, encryptPassword);
        if (user == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户名密码不匹配");
        }
        UserVo userVo = new UserVo();
        userVo.setAuth(user.getAuth());
        userVo.setUsername(user.getUsername());
        request.getSession().setAttribute(USER_LOGIN_STATE, userVo);
        return userVo;
    }


}
