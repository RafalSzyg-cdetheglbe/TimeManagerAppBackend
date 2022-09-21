package com.start.timemanager.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.GONE,reason = "No such object with given id")
public class DeletedExeption extends  RuntimeException {
    public DeletedExeption(String message) {
        super(message);
    }
}