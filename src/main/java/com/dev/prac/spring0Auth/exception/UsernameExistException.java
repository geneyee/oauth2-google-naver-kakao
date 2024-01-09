package com.dev.prac.spring0Auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND, reason = "username exist")
public class UsernameExistException extends RuntimeException {

    public UsernameExistException(String message) {
        super(message);
    }
}
