package com.virgo.common.exception;

public class BusinessException extends BaseException {

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum);
    }

    public BusinessException(ExceptionEnum exceptionEnum, Object... messages) {
        super(exceptionEnum, messages);
    }

    public BusinessException(ResultEnum resultEnum, String message, Throwable cause) {
        super(resultEnum, message, cause);
    }

    public BusinessException(ResultEnum resultEnum, Throwable cause) {
        super(resultEnum, cause);
    }

    public BusinessException(ResultEnum resultEnum, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(resultEnum, message, cause, enableSuppression, writableStackTrace);
    }
}
