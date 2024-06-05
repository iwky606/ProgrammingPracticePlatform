package com.oneq.programmingpracticeplatform.model.vo;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private long id;
    private String username;
    private AuthEnum auth;
    private static final long serialVersionUID = 1L;
}
