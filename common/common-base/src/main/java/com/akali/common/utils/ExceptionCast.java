package com.akali.common.utils;


import com.akali.common.code.ResultCode;
import com.akali.common.model.exception.MyCustomException;

public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new MyCustomException(resultCode);
    }
}
