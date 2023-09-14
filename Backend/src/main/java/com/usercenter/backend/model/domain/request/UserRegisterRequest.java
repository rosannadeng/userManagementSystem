package com.usercenter.backend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求实体
 * @author dengyuxin
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 9204482978683617696L;

    private  String userAccount;
    private String userPassword;
    private String checkPassword;

}
