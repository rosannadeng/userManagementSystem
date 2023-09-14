package com.usercenter.backend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 9204482978683617696L;

    private  String userAccount;
    private String userPassword;

}
