package com.example.fitgo.controller.validations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class APIExceptions extends RuntimeException {

    public APIExceptions(String message) {
        super(message);
    }
}
