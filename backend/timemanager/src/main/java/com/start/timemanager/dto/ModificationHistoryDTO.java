package com.start.timemanager.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ModificationHistoryDTO {
    private LocalDateTime modificationDate;
    private Long userId;
    private Long objectId;
    private Long objectTypeId;
    private Long modyficationTypeId;
}
