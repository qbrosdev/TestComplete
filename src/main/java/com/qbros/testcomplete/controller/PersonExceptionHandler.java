package com.qbros.testcomplete.controller;

import com.qbros.testcomplete.service.models.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * It is better to have specific exception handlers per domain, so that
 * out exception handlers logic do not collide and also makes our exception handler class smaller.
 */
@ControllerAdvice
@Slf4j
public class PersonExceptionHandler {

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResp handle(SystemException systemException) {
        log.warn("SystemException Err code: {}", systemException.getErrorCode(), systemException);
        ErrorResp resp = new ErrorResp(systemException.getMessage());
        resp.addAttribute("Err Code", systemException.getErrorCode());
        return resp;
    }


}
