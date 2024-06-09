package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private long id;
    private String username;
    private AuthEnum auth;
    private static final long serialVersionUID = 1L;

    public static UserVo objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    public static User voToObj(UserVo userVo) {
        if (userVo == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        return user;
    }
}
