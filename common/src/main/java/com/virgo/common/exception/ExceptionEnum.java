package com.virgo.common.exception;

public interface ExceptionEnum {

    int getCode();

    String getMessage();

    void setMessage(Object... message);
}
