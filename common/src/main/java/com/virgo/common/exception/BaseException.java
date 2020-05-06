package com.virgo.common.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
class BaseException extends RuntimeException {
    private final ExceptionEnum exceptionEnum;

    BaseException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    BaseException(ExceptionEnum exceptionEnum, Object... messages) {
        if (messages != null)
            exceptionEnum.setMessage(messages);
        this.exceptionEnum = exceptionEnum;
    }


    BaseException(ExceptionEnum exceptionEnum, String message, Throwable cause) {
        super(message, cause);
        this.exceptionEnum = exceptionEnum;
    }

    BaseException(ExceptionEnum exceptionEnum, Throwable cause) {
        super(cause);
        this.exceptionEnum = exceptionEnum;
    }

    BaseException(ExceptionEnum exceptionEnum, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
