package com.start.timemanager.service.interfaces;

import com.start.timemanager.dto.BucketMemberDTO;
import com.start.timemanager.model.Bucket;
import com.start.timemanager.model.BucketMember;

import java.util.List;

public interface IBucketMemberService {

    public Boolean checkBucketMember(Long userId, Long bucketId);

    public List<BucketMember> getBucketMembers(Long bucketId);

    public List<Bucket> getUserBuckets(String userId);

    public BucketMemberDTO addBucketMember(BucketMemberDTO bucketMemberDTO);

    public void deleteBucketMember(Long id);
}
