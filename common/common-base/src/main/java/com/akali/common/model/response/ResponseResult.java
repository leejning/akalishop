package com.akali.common.model.response;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ResponseResult<T> implements Response {
    T data;
    //操作是否成功
    private boolean success = SUCCESS;


    //操作代码
    private int code = SUCCESS_CODE;



    //提示信息
    String message;

    public ResponseResult(ResultCode resultCode){
        if(resultCode!=null) {
            this.success = resultCode.success();
            this.code = resultCode.code();
            this.message = resultCode.message();
        }
    }
    public ResponseResult(ResultCode resultCode,T entity){
        if(resultCode!=null) {
            this.success = resultCode.success();
            this.code = resultCode.code();
            this.message = resultCode.message();
        }
        this.data = entity;
    }
    public ResponseResult(int code,String message,boolean success){
        this.code = code;
        this.message = message;
        this.success = success;
    }


    public static<T> ResponseResult<T> SUCCESS(T entity){
        return new ResponseResult<>(CommonCode.SUCCESS,entity);
    }
    public static<T> ResponseResult<T> SUCCESS(){
        return new ResponseResult<>(CommonCode.SUCCESS);
    }
    public static<T> ResponseResult<T> SUCCESS(String message){
        return new ResponseResult<T>(SUCCESS_CODE,message,true);
    }
    public static<T> ResponseResult<T> SUCCESS(ResultCode resultCode){
        return new ResponseResult<>(resultCode);
    }



    public static ResponseResult FAIL(){
        return new ResponseResult<>(CommonCode.FAIL);
    }
    public static <T> ResponseResult<T> FAIL(ResultCode resultCode){
        return new ResponseResult<>(resultCode);
    }
    public static <T> ResponseResult<T> FAIL(ResultCode resultCode,T data){
        return new ResponseResult<>(resultCode,data);
    }
    public static ResponseResult FAIL(int code, String defaultMessage) {
        return new ResponseResult<>(code, defaultMessage,false);
    }
}
