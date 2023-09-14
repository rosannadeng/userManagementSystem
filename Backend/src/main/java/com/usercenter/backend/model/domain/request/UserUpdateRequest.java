package com.usercenter.backend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 9204482978683617696L;

    private String userPassword;
    private String checkPassword;
    private String username;
    private String avatarUrl;
    private String email;
    private Integer gender;
    private String phone;
}
