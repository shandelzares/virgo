package com.virgo.common.exception;

public class SystemException extends BaseException {

    public SystemException() {
        super(ResultEnum.SYSTEM_ERROR);
    }

    public SystemException(ExceptionEnum exceptionEnum, Object... messages) {
        super(exceptionEnum, messages);
    }

    public SystemException(String message) {
        super(ResultEnum.SYSTEM_ERROR, message);
    }

    public SystemException(String message, Throwable cause) {
        super(ResultEnum.SYSTEM_ERROR, message, cause);
    }

    public SystemException(Throwable cause) {
        super(ResultEnum.SYSTEM_ERROR, cause);
    }

    public SystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ResultEnum.SYSTEM_ERROR, message, cause, enableSuppression, writableStackTrace);
    }
}
