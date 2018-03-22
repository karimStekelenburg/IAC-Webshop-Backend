package com.ElegantDevelopment.iacWebshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameAlreadyTakenException extends RuntimeException{
    private String username;

    public UsernameAlreadyTakenException(String username) {
        super(String.format("Username %s is already taken", username));
    }

    public String getUsername(){
        return username;
    }

}
