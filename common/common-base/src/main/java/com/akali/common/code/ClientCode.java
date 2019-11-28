package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName ClientCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public enum ClientCode implements ResultCode{
    /**
     *
     */
    CLIENT_NOT_EXIST(false,70001,"不存在该服务！"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private ClientCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, ClientCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, ClientCode> builder = ImmutableMap.builder();
        for (ClientCode commonCode : values()) {
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
