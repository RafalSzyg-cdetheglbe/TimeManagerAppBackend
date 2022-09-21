package com.start.timemanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "No such object with given id")
public class NotFoundException extends  RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
