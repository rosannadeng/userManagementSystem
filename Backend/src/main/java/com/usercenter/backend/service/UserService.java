package com.usercenter.backend.service;

import com.usercenter.backend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.usercenter.backend.model.domain.request.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;


/**
* 用户服务
* @author YuxinDeng
*
 * */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
    * */
    long userRegister(String userAccount,
                      String userPassword,
                      String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
    /**
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);


    /**
     * @param updateInfo
     * @return
     */
    User updateUserDetails(User existingUser, UserUpdateRequest updateInfo) ;
}
