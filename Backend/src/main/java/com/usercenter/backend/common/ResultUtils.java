package com.usercenter.backend.common;

public class ResultUtils {
    /**
     * @param data
     * @param <T>
     * @return
     */
    public static<T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }


    /**
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description){
        return new BaseResponse(code,null,message,description);
    }

    /**
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message, String description){
        return new BaseResponse(errorCode.getCode(),null, message,description);
    }

    /**
     * @param errorCode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description){

        return new BaseResponse(errorCode.getCode(),null, errorCode.getMessage(),description);
    }
}
