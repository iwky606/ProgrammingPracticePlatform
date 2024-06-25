package com.oneq.programmingpracticeplatform.model.dto.user;

import com.oneq.programmingpracticeplatform.model.enums.AuthEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户注册请求参数")
public class UserRegisterRequest implements Serializable {
    @ApiModelProperty(value = "用户名", example = "wky", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "wky123456", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", example = "wky123456", required = true)
    private String checkPassword;

    private AuthEnum auth;
    private static final long serialVersionUID = 1L;
}
