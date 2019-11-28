package com.akali.common.model.exception;


import com.akali.common.code.ResultCode;

/**
 * @ClassName MyCustomException.java
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/27 0027
 * @Version V1.0.0
**/
public class MyCustomException extends RuntimeException {

    //错误代码
    ResultCode resultCode;
    

    public MyCustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    
    public ResultCode getResultCode(){
        return resultCode;
    }


}
