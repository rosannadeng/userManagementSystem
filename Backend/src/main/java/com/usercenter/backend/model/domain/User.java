package com.usercenter.backend.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String userAccount;

    private String avatarUrl;

    private Date createTime;

    private String email;

    private Integer gender;

    private String phone;

    @TableLogic
    private Integer isDelete;

    private Integer userRole;

    private Integer userStatus;

    private String   userPassword;

    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
