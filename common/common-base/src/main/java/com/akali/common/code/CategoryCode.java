package com.akali.common.code;


import com.google.common.collect.ImmutableMap;

public enum CategoryCode implements ResultCode{
    /**
     *
     */
    ,
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CategoryCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, CategoryCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, CategoryCode> builder = ImmutableMap.builder();
        for (CategoryCode commonCode : values()) {
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
