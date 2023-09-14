package com.usercenter.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usercenter.backend.Mapper.UserMapper;
import com.usercenter.backend.model.domain.User;
import com.usercenter.backend.model.domain.request.UserUpdateRequest;
import com.usercenter.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static com.usercenter.backend.constant.UserConstant.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /*
    * 混淆密码
     */
    private static final String SALT = "ros";

    /**
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            log.info("user register field, lack of vairable");
            return -1;
        }
        if (userAccount.length()<4){
            log.info("useraccount not satisfy bound");
            return -1;
        }
        if (userPassword.length()<8 || checkPassword.length()<8){
            log.info("userPassword incorrect");
            return -1;
        }

        //no special character in username
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return -1;
        }
        //double check password setting
        if (!userPassword.equals(checkPassword)){
            return -1;
        }

        //no repeat account
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count>0){
            log.info("user account exist");
            return -1;
        }
        //encrypt
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //add data
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult){
            log.info("save Failed");
            return -1;
        }
        return user.getId();
    }


    /**
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request)
    {
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if (userAccount.length()<4){
            return null;
        }
        if (userPassword.length()<8){
            return null;
        }

        //no special character in username
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return null;
        }

        //encrypt
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //search user in db
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null){
            log.info("user login failed, userAcount doesn't match password");
            return null;
        }


        User safetyUser = getSafetyUser(user);
        //record login state
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);
        log.info("user login sucess");
        return safetyUser;
    }


    /**
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request){
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if (originUser == null){
            return null;
        }
            //4. 用户脱敏
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }
    public void updateUserDetails(User existingUser, User updatedInfo) {

        // Set the update time to current time after making updates
        existingUser.setUpdateTime(new Date());

        if (updatedInfo.getUsername() != null) {
            existingUser.setUsername(updatedInfo.getUsername());
        }

        if (updatedInfo.getAvatarUrl() != null) {
            existingUser.setAvatarUrl(updatedInfo.getAvatarUrl());
        }

        if (updatedInfo.getEmail() != null) {
            existingUser.setEmail(updatedInfo.getEmail());
        }

        if (updatedInfo.getGender() != null) {
            existingUser.setGender(updatedInfo.getGender());
        }

        if (updatedInfo.getPhone() != null) {
            existingUser.setPhone(updatedInfo.getPhone());
        }

        if (updatedInfo.getUserPassword() != null) {
            existingUser.setUserPassword(updatedInfo.getUserPassword());
        }

        existingUser.setUpdateTime(new Date());
        saveOrUpdate(existingUser);
        return existingUser;
    }



}
