package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName AdminCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public enum AdminCode implements ResultCode{
    /**
     *
     */
    ADMIN_LOGIN_FAIL(false,10110,"管理员登录失败！"),
    DENY_REQUEST(false,10120,"Need Authorities！"),
    ADMIN_CREDENTIAL_ERROR(false,10192,"密码错误！"),
    HAS_NOT_ADMIN_PERMISSION(false,10190,"该账户没有任何管理员权限"),
    ADMIN_ACCOUNT_NOT_EXIST(false,10191,"管理员账号不存在"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private AdminCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, AdminCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, AdminCode> builder = ImmutableMap.builder();
        for (AdminCode commonCode : values()) {
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
