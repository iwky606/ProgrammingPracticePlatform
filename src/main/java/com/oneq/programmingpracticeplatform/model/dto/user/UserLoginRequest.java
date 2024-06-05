package com.oneq.programmingpracticeplatform.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private String username;
    private String password;
    private static final long serialVersionUID = 1L;
}
