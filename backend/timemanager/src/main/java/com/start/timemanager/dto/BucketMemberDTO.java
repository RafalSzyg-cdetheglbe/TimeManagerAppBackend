package com.start.timemanager.dto;

import lombok.Data;

@Data
public class BucketMemberDTO {
    private Long id;
    private Long userId;
    private Long bucketId;
    private boolean isDeleted;
}
