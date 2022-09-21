package com.start.timemanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "Invalid Data")
public class NotValidExeption extends  RuntimeException{
    public NotValidExeption(String message){
        super(message);
    }
}

