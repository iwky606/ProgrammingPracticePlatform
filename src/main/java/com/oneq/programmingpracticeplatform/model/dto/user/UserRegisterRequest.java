package com.oneq.programmingpracticeplatform.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private String username;
    private String password;
    private String checkPassword;
    private int auth;
    private static final long serialVersionUID = 1L;
}
