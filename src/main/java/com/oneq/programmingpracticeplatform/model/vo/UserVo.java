package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import lombok.Data;

@Data
public class UserVo {
    private long id;
    private String username;
    private AuthEnum auth;
}
