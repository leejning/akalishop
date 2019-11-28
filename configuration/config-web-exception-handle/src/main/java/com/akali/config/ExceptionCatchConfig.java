package com.akali.config;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ResultCode;
import com.akali.common.model.exception.MyCustomException;
import com.akali.common.model.response.ResponseResult;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

/** 
 * @ClassName ExceptionCatch.java
 * @Description: TODO 
 * @Author Administrator
 * @Date 2019/9/27 0027
 * @Version V1.0.0
**/
@ControllerAdvice
@Slf4j
public class ExceptionCatchConfig {

//    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //定义map，配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();


    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public ResponseResult customException(MyCustomException customException){
        //记录日志
        log.error("catch exception:{}",customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult beanPropertyBindingResult(MethodArgumentNotValidException methodArgumentNotValidException){
        log.error("catch exception:{}",methodArgumentNotValidException.getMessage());
        ObjectError objectError = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0);
        return ResponseResult.FAIL(406,objectError.getDefaultMessage());
    }



//    @ExceptionHandler(Exception.class)
//    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        log.error("catch exception:{}",exception.getMessage());
        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }

        //从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode !=null){
            return new ResponseResult(resultCode);
        }else{
            //返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    static {
        //定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
    }
}
