package com.start.timemanager.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BucketDTO {
    private Long id;
    private String name;
    private String description;
    private int maxTasks;
    private int activeTasks;
    private Long userId;
    private LocalDateTime lastChange;
    private LocalDateTime createdDate;
    private boolean isDeleted;
    private List<Long> tasks;
}
