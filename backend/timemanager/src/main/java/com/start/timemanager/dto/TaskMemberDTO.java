package com.start.timemanager.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskMemberDTO {
    private Long id;
    private Long userId;
    private Long taskId;
    private LocalDateTime notificationDate;
    private boolean isDeleted;
}
