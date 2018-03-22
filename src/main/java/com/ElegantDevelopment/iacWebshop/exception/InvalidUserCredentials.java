package com.ElegantDevelopment.iacWebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class InvalidUserCredentials extends RuntimeException{
    public InvalidUserCredentials(String falseUsername) {

        super(String.format("No account found with username %s", falseUsername));
    }
}
