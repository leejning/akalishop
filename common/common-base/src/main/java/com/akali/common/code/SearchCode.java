package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName SearchCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/15 0015
 * @Version V1.0
 **/
public enum  SearchCode  implements ResultCode{
    /**
     *
     */
    KEY_CANT_BE_NULL(false,80101,"关键字不能为空"),
    IMAGE_CONTENT_ILLEGAL(false,80102,"图片文件内容不符合要求"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private SearchCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, SearchCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, SearchCode> builder = ImmutableMap.builder();
        for (SearchCode commonCode : values()) {
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

