package com.akali.common.code;

import lombok.ToString;


@ToString
public enum CommonCode implements ResultCode{
    /**
     *
     */
    INVALID_PARAM(false,403,"非法参数！"),
    SUCCESS(true,20000,"操作成功！"),
    TIMEOUT(false,30001,"您的网络有问题！"),
    FAIL(false,500,"操作失败！"),
    UNAUTHENTICATED(false,403,"此操作需要登陆系统！"),
    UNAUTHORISE(false,403,"权限不足，无权操作！"),
    SERVER_ERROR(false,500,"抱歉，系统繁忙，请稍后重试！"),
    SERVICE_DOWN_LEVEL(false,30020,"服务限流控制！"),
    TO_BE_CONTINUE(false,20012,"敬请期待"),
    APP_UP_FAIL(false,10001,"应用启动失败"),
    POST_REDO_ERROR(false,10002,"您已经提交过了，请勿重复提交！"),
    ;
//    private static ImmutableMap<Integer, CommonCode> codes ;
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CommonCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
