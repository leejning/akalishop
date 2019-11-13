package com.akali.config.exception.util;


import com.akali.common.code.ResultCode;
import com.akali.config.exception.MyCustomException;

public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        throw new MyCustomException(resultCode);
    }
}
