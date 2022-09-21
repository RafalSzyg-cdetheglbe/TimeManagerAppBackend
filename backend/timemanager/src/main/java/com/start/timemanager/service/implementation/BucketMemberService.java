package com.start.timemanager.service.implementation;

import com.start.timemanager.dto.BucketMemberDTO;
import com.start.timemanager.error.DeletedExeption;
import com.start.timemanager.service.interfaces.IBucketMemberService;
import com.start.timemanager.model.Bucket;
import com.start.timemanager.model.BucketMember;
import com.start.timemanager.model.User;
import com.start.timemanager.repository.BucketMemberRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BucketMemberService implements IBucketMemberService {

    private final BucketMemberRepository bucketMemberRepository;

    public BucketMemberService(BucketMemberRepository bucketMemberRepository) {
        this.bucketMemberRepository = bucketMemberRepository;
    }

    public Boolean checkBucketMember(Long userId, Long bucketId) {
        for (BucketMember bucketMember : bucketMemberRepository.findAll())
            if (bucketMember.getBucket().getId().equals(bucketId) && bucketMember.getUser().getId().equals(userId))
                return true;
        return false;
    }

    public List<BucketMember> getBucketMembers(Long bucketId) {
        List<BucketMember> bucketMembers = new ArrayList<BucketMember>();
        for (BucketMember bucketMember : bucketMemberRepository.findAll())
            if (bucketMember.getBucket().getId().equals(bucketId))
                bucketMembers.add(bucketMember);

        return bucketMembers;
    }

    public List<Bucket> getUserBuckets(String userId) {
        List<Bucket> buckets = new ArrayList<Bucket>();
        for (BucketMember bucket : bucketMemberRepository.findAll())
            if (bucket.getUser().getEmail().equals(userId))
                buckets.add(bucket.getBucket());
        return buckets;
    }

    public BucketMemberDTO addBucketMember(BucketMemberDTO bucketMemberDTO) {

        User user = new User();
        Bucket bucket = new Bucket();
        user.setId(bucketMemberDTO.getUserId());
        bucket.setId(bucketMemberDTO.getBucketId());
        BucketMember bucketMember = new BucketMember(user, bucket, false);
        bucketMemberRepository.save(bucketMember);
        bucketMemberDTO.setId(bucketMember.getId());
        return bucketMemberDTO;
    }

    public void deleteBucketMember(Long id) {
        try {
            this.bucketMemberRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletedExeption("already deleted");
        }
    }
}
