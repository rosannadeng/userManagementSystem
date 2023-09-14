package com.usercenter.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usercenter.backend.common.BaseResponse;
import com.usercenter.backend.common.ErrorCode;
import com.usercenter.backend.common.ResultUtils;
import com.usercenter.backend.exception.BusinessException;
import com.usercenter.backend.model.domain.User;
import com.usercenter.backend.model.domain.request.UserLoginRequest;
import com.usercenter.backend.model.domain.request.UserRegisterRequest;
import com.usercenter.backend.model.domain.request.UserUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.usercenter.backend.service.UserService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.usercenter.backend.constant.UserConstant.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null
                || StringUtils.isAnyBlank(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword())) {
            return ResponseEntity.badRequest().build();
        }
        Long result = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getUserPassword(), userRegisterRequest.getCheckPassword());
        return ResponseEntity.ok(result);
    }

    /**
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int res = userService.userLogout(request);
        return ResultUtils.success(res);
    }
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(@RequestParam(required = false) String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"没有管理员权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user-> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable long id, HttpServletRequest request) {
        if (!isAdmin(request) || id <= 0) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        boolean deleted = userService.removeById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    @PostMapping("/update")
    public BaseResponse<User> updateUserDetails(@RequestBody UserUpdateRequest updateInfo, HttpServletRequest request) {
        // check if the logged-in user is the same as the updating user or if the user has admin privileges
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (isAdmin(request)) {
            User updatedUser = userService.updateUserDetails(currentUser,updateInfo);
            if (updatedUser != null) {
                return ResultUtils.success(updatedUser);
            } else {
                return ResultUtils.error(ErrorCode.UPDATE_ERROR);
            }
        } else {
            throw new BusinessException(ErrorCode.NO_AUTH, "Unauthorized update attempt");
        }
    }
}
