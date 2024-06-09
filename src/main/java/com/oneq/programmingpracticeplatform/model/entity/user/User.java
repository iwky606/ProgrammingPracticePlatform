package com.oneq.programmingpracticeplatform.model.entity.user;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    private String username;
    private String password;
    private AuthEnum auth;
    private long createTime;
    private long updateTime;
}
