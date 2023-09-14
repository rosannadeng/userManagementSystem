package com.usercenter.backend.common;

public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求为空",""),
    NO_AUTH(40101,"无权限",""),
    NOT_LOGIN(40100,"未登录",""),

    UPDATE_ERROR(50001,"更新出错",""),
    SYSTEM_ERROR(50000,"系统报错","");


    private final int code;

    private final String message;

    private final String description;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    ErrorCode(int code, String message, String description){
        this.code = code;
        this.message = message;
        this.description = description;
    }


}
