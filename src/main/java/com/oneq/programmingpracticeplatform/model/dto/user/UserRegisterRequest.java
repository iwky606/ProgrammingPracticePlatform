package com.oneq.programmingpracticeplatform.model.dto.user;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private String checkPassword;
    private short auth;
}
