package com.start.timemanager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Long userId;
    private Long bucketId;
    private Long stateId;
    private LocalDateTime creaDate;
    private LocalDateTime deadline;
    private Long realizationTime;
    private Long priorityId;
    private LocalDateTime estimatedEndTime;
    private boolean isDeleted;
}