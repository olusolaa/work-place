package com.decagon.employeemanagementapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public static final Long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
