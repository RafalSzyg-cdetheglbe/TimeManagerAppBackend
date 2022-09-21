package com.start.timemanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private Long userRole;

    private boolean isActive;
    private int activeBuckets;
    private boolean isDeleted;
    private LocalDateTime createdDate;

}
