package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.user.User;
import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT 1 FROM users WHERE username=#{username}")
    Integer queryUser(String username);

    int insertUser(User user);

    @Select("SELECT * FROM users WHERE username=#{username} AND password=#{password}")
    User queryUserPassword(String username, String password);

    int updateUserAuth(String username, AuthEnum auth);
}
