package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName UploadCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/

public enum UploadCode implements ResultCode{
    /**
     *
     */
    IMAGE_TYPE_ILLEGAL(false,500001,"图片格式不符合要求"),
    IMAGE_CONTENT_ILLEGAL(false,500002,"图片文件内容不符合要求"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private UploadCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, UploadCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, UploadCode> builder = ImmutableMap.builder();
        for (UploadCode commonCode : values()) {
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
