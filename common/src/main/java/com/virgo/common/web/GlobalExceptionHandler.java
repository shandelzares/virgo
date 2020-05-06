package com.virgo.common.web;

import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.SystemException;
import com.virgo.common.response.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @apiNote 统一异常处理
 */
@RestController
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData<?> paramsErrorHandler(final Exception e) {
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
        List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldError.stream().map(msg -> msg.getField() + ":" + msg.getDefaultMessage()).collect(Collectors.toList());
        log.warn("参数错误 {}", errorMessages);
        return ResultData.fail(400, errorMessages.toString());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResultData<?> paramsErrorHandler2(final Exception e) {
        final String message;
        if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException ex = (MissingServletRequestParameterException) e;
            message = ex.getParameterName() + " request parameter 参数不存在";
        } else if (e instanceof MissingRequestHeaderException) {
            MissingRequestHeaderException ex = (MissingRequestHeaderException) e;
            message = ex.getHeaderName() + " request header 参数不存在";
        } else if (e instanceof MissingMatrixVariableException) {
            MissingMatrixVariableException ex = (MissingMatrixVariableException) e;
            message = ex.getVariableName() + " request variable 参数不存在";
        } else if (e instanceof MissingRequestCookieException) {
            MissingRequestCookieException ex = (MissingRequestCookieException) e;
            message = ex.getCookieName() + " request cookie name 参数不存在";
        } else if (e instanceof UnsatisfiedServletRequestParameterException) {
            UnsatisfiedServletRequestParameterException ex = (UnsatisfiedServletRequestParameterException) e;
            log.warn(ex.getMessage(), ex);
            message = "参数错误";
        } else {
            message = "参数错误";
        }
        log.warn("参数错误 ", e);
        return ResultData.fail(400, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResultData<?> params2ErrorHandler(final Exception e) {
        ConstraintViolationException exs = (ConstraintViolationException) e;
        List<String> errorMessages = exs.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        log.warn("参数错误 {}", errorMessages);
        return ResultData.fail(400, errorMessages.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResultData<?> systemErrorHandler(final Exception e) {
        log.error("系统异常", e);
        return ResultData.fail(500, "系统异常");
    }

    @ExceptionHandler(BusinessException.class)
    public ResultData<?> businessExceptionHandler(final BusinessException e) {
        log.error("业务异常", e);
        return ResultData.fail(e.getExceptionEnum().getCode(), e.getExceptionEnum().getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public ResultData<?> systemExceptionHandler(final SystemException e) {
        log.error("系统异常", e);
        return ResultData.fail(e.getExceptionEnum().getCode(), e.getExceptionEnum().getMessage());
    }
}
