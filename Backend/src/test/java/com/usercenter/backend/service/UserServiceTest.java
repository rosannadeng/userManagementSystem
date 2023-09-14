package com.usercenter.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.usercenter.backend.model.domain.User;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

//    @Test
//    void testAddUser() {
//        User user = new User();
//        user.setUsername("adkj");
//        user.setUserAccount("123");
//        user.setAvatarUrl("https://s3-imfile.feishucdn.com/static-resource/v1/0248d58b-7583-4574-890c-31452c99ebfg~?image_size=noop&cut_type=&quality=&format=image&sticker_format=.webp");
//        user.setGender(0);
//        user.setUserPassword("xxx");
//        user.setEmail("123");
//        user.setPhone("456");
//
//        boolean res = userService.save(user);
//        System.out.println(user.getId());
//        Assertions.assertTrue(res);
//    }

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "dogYupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

    }

}