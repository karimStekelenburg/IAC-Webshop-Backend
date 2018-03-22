package com.ElegantDevelopment.iacWebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class InvalidPasswordCredentials extends RuntimeException{
    public InvalidPasswordCredentials(String username) {

        super(String.format("Not a valid password for username %s", username));
    }
}
