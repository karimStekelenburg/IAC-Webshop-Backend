package com.ElegantDevelopment.iacWebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class SessionTimeoutException extends RuntimeException {
    public SessionTimeoutException() {
        super("Session expired");
    }
}
