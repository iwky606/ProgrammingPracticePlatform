package com.oneq.programmingpracticeplatform.mapper;

import com.oneq.programmingpracticeplatform.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT 1 FROM users WHERE username=#{username}")
    Integer queryUser(String username);

    int insertUser(User user);
}
