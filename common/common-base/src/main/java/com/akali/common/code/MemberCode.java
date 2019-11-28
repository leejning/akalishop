package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName MemberCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
public enum MemberCode implements ResultCode{
    /**
     *
     */
    MEMBER_ACCOUNT_NOT_EXIST(false,80012,"该账户没有任何管理员权限"),
    MEMBER_PASSWORD_ERROR(false,80013,"密码错误"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private MemberCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, MemberCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, MemberCode> builder = ImmutableMap.builder();
        for (MemberCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
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

