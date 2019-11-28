package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName RegistryCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
public enum RegistryCode implements ResultCode{
    /**
     *
     */
    EMAIL_VERIFY_CODE_ERROR(false,60010,"email验证码不正确"),
    IMAGE_VERIFY_CODE_ERROR(false,60011,"图片验证码不正确"),
    TWO_PASSWORK_NOT_CONSIST(false,60012,"两次密码不一致"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private RegistryCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, RegistryCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, RegistryCode> builder = ImmutableMap.builder();
        for (RegistryCode commonCode : values()) {
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
