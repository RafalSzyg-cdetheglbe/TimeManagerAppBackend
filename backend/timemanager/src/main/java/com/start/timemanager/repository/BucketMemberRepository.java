package com.start.timemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.start.timemanager.model.BucketMember;

import java.util.List;

@Repository
public interface BucketMemberRepository extends JpaRepository<BucketMember, Long> {
    final String bucketQuery = "SELECT b.* FROM bucket_members b WHERE b.bucket_id=:id";

    final String userQuery = "SELECT b.* FROM bucket_members b WHERE b.user_id=:id";



    @Query(value = bucketQuery, nativeQuery = true)
    List<BucketMember> findByBucketId(Long id);

    @Query(value = userQuery, nativeQuery = true)
    List<BucketMember> findByUserId(Long id);


}
