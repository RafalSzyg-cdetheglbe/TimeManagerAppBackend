package com.start.timemanager.service.interfaces;

import com.start.timemanager.dto.BucketDTO;
import java.util.List;

public interface IBucketService {
    public List<BucketDTO> getBuckets(String userId);

    public BucketDTO getBucket(Long id, String email);

    public BucketDTO addBucket(BucketDTO bucketTransfer, String email);

    public BucketDTO updateBucket(BucketDTO bucketTransfer,Long id, String email);

    public Long deleteBucket(Long id, String email);
}
