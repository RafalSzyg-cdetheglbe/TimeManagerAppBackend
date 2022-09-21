package com.start.timemanager.model;

import lombok.Data;

@Data
public class Password {

    private String email;
    private String oldPassword;
    private String newPassword;
}
