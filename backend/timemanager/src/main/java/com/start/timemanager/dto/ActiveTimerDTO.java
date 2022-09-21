package com.start.timemanager.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ActiveTimerDTO {
    private Long id;
    private Long taskId;
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime startPause;
    private boolean isPaused;
    private int realizationTime;
    private boolean isDeleted;
}
