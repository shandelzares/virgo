package com.virgo.common.exception;


import java.text.MessageFormat;

public enum ResultEnum implements ExceptionEnum {
    SUCCESS(200, "成功"),
    PATH_NOT_FOUND(404, "请求地址不存在"),

    /**
     * 参数错误4xxx
     */
    PARAM_ERROR(40000, "参数错误"),

    /**
     * member
     */
    INVITATION_CODE_ERROR(51000,"{}"),
    INVITATION_CODE_NOT_FOUND(51001,"邀请码未找到"),
    INVITATION_CODE_USED(51002,"邀请码次数已经用完"),
    INVITATION_CODE_UN_STARTED(51003,"邀请码还未开始"),
    INVITATION_CODE_FINISHED(51004,"邀请码已失效"),

    SEND_SMS_ERROR(51100,"短信发送失败"),
    SMS_SEND_FREQUENTLY(51101,"短信发送过于频繁"),
    SMS_CODE_NOT_FOUND(51102,"短信验证码未找到"),
    SMS_INCORRECT(51103,"短信验证码不正确"),

    MEMBER_NOT_FOUND(51200,"用户未找到"),
    ACCOUNT_LOCKED(51201,"账号锁定"),




    /**
     * 系统错误99xx
     */
    SYSTEM_ERROR(99999, "系统异常"),
    UNKNOWN(-1, "未定义错误");

    private final int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(Object... messages) {
        this.message = MessageFormat.format(message, messages);
    }

}
